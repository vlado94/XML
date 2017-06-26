package com.endopoints;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.banka.Banka;
import com.banka.BankaService;
import com.config.BankaClient;
import com.mt102.GetMT102Request;
import com.mt102.MT102;
import com.mt102.MT102Service;
import com.mt102.PojedinacnoPlacanjeMT102;
import com.mt102.ZaglavljeMT102;
import com.mt103.GetMT103Request;
import com.mt103.MT103;
import com.mt103.MT103Service;
import com.mt900.MT900;
import com.mt910.GetMT910Request;
import com.mt910.GetMT910Response;
import com.mt910.MT910;
import com.mt910.MT910Service;
import com.nalog.GetNalogRequest;
import com.nalog.GetNalogResponse;
import com.nalog.Nalog;
import com.nalog.NalogService;
import com.presek.GetPresekResponse;
import com.presek.Presek;
import com.presek.StavkaPreseka;
import com.presek.ZaglavljePreseka;
import com.racun.Racun;
import com.racun.RacunService;
import com.temp.NaloziZaglavlje;
import com.zahtevZaDobijanjeNaloga.GetZahtevZaDobijanjeNalogaRequest;
import com.zahtevZaDobijanjeNaloga.GetZahtevZaDobijanjeNalogaResponse;
import com.zahtevzadobijanjeizvoda.GetZahtevZaDobijanjeIzvodaRequest;


@Endpoint
@Component
public class BankEndpoint {
	private static final String NAMESPACE_URI = "http://nalog.com";
	private static final String NAMESPACE_URI2 = "http://mt910.com";
	private static final String NAMESPACE_URI3 = "http://zahtevZaDobijanjeIzvoda.com";

	private static final String NAMESPACE_URI4 = "http://mt103.com";
	private static final String NAMESPACE_URI5 = "http://mt102.com";

	private static final String NAMESPACE_URI6 = "http://zahtevZaDobijanjeNaloga.com";
	@Autowired
	BankaClient bankaClient;

	@Autowired
	MT102Service MT102Service;

	@Autowired
	BankaService bankaService;

	@Autowired
	RacunService racunService;

	@Autowired
	NalogService nalogService;

	@Autowired
	MT103Service MT103Service;

	@Autowired
	MT910Service MT910Service;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getNalogRequest")
	@ResponsePayload
	public GetNalogResponse getNalog(@RequestPayload GetNalogRequest request) {
		if(!ValidacijaSema.validirajSemu(request, "nalog")) {
			System.out.println("Nevalidan nalog");
			return null;
		}
		GetNalogResponse response = new GetNalogResponse();
		Nalog primljenNalog = request.getNalog();
		primljenNalog.setObradjen(false);

		String kodBankeDuznika = primljenNalog.getRacunDuznika().substring(0, 3);
		String kodBankePrimaoca = primljenNalog.getRacunPrimaoca().substring(0, 3);

		Banka bankaDuznika = bankaService.findByKod(kodBankeDuznika);
		Banka bankaPrimaoca = bankaService.findByKod(kodBankePrimaoca);

		Racun racunDuznika = racunService.findByBrojRacuna(primljenNalog.getRacunDuznika());
		Racun racunPrimaoca = racunService.findByBrojRacuna(primljenNalog.getRacunPrimaoca());

		if (kodBankePrimaoca.equals(kodBankeDuznika)) { // iz iste tj moje
			System.out.println("Iz iste banke");
			primljenNalog.setObradjen(true);
			primljenNalog = nalogService.save(primljenNalog);
			MathContext mc = new MathContext(2);
			racunDuznika.setTrenutnoStanje(racunDuznika.getTrenutnoStanje().subtract(primljenNalog.getIznos(), mc));

			racunService.save(racunDuznika);

			BigDecimal noviPrimaoca = racunPrimaoca.getTrenutnoStanje().add(primljenNalog.getIznos());
			racunPrimaoca.setTrenutnoStanje(noviPrimaoca);

			racunService.save(racunPrimaoca);

		} else {
			System.out.println("Pravi neku od poruka");

			if (primljenNalog.isHitno() || primljenNalog.getIznos().compareTo(BigDecimal.valueOf(250000)) == 1) {// hitno
				System.out.println("Hitno");

				MT103 mt103 = getMT103IzNaloga(bankaDuznika, bankaPrimaoca, primljenNalog);

				MT103Service.save(mt103);
				racunDuznika.setRezervisano(racunDuznika.getRezervisano().add(primljenNalog.getIznos()));
				racunService.save(racunDuznika);

				MT900 mt900 = bankaClient.sendMT103(mt103);
				if(mt900 == null)
					return null;
				primljenNalog.setObradjen(true);
				primljenNalog = nalogService.save(primljenNalog);
				MathContext mc = new MathContext(2);
				racunDuznika.setTrenutnoStanje(racunDuznika.getTrenutnoStanje()
						.subtract(racunDuznika.getRezervisano().subtract(mt900.getIznos()), mc));

				racunDuznika.setRezervisano(racunDuznika.getRezervisano().subtract(mt900.getIznos()));
				racunService.save(racunDuznika);
			} else {
				System.out.println("Nije hitno");
				racunDuznika.setRezervisano(racunDuznika.getRezervisano().add(primljenNalog.getIznos()));
				racunService.save(racunDuznika);

				List<MT102> sviMT102 = MT102Service.findAll();

				for (int i = 0; i < sviMT102.size(); i++) {
					if (sviMT102.get(i).getZaglavljeMT102().getSwiftKodBankeDuznika().equals(bankaDuznika.getSwiftKod())
							&& sviMT102.get(i).getZaglavljeMT102().getSwiftKodBankePoverioca()
									.equals(bankaPrimaoca.getSwiftKod())
							&& sviMT102.get(i).getStatus() == false) {
						// POSTOJI MT102 koji nije obradjen za vezan je za
						// odredjene 2 banke
						MT102 pronadjenMT102 = sviMT102.get(i);
						pronadjenMT102.setStatus(false);
						PojedinacnoPlacanjeMT102 placanje = getPojedinacnoPlacanjeIzNaloga(primljenNalog);
						pronadjenMT102.getPojedinacnoPlacanjeMT102().add(placanje);
						MT102Service.save(pronadjenMT102);
						break;
						// odgovor treba da promjeni status u true kad se
						// posalje
					} else { // nema mt102
						MT102 mt102 = new MT102();
						mt102.setStatus(false);
						ZaglavljeMT102 zaglavlje = getZaglavljeMT102IzNaloga(bankaDuznika, bankaPrimaoca,
								primljenNalog);
						mt102.setZaglavljeMT102(zaglavlje);
						PojedinacnoPlacanjeMT102 placanje = getPojedinacnoPlacanjeIzNaloga(primljenNalog);
						mt102.getPojedinacnoPlacanjeMT102().add(placanje);

						// zaglavlje.setDatum(primljenNalog.getDatumNaloga());
						// fali zaglavlje.setDatum();
						MT102Service.save(mt102);
						break;
					}
				}

			}
		}
		// bankaClient.sendNalog();
		return response;
	}

	private MT103 getMT103IzNaloga(Banka bankaDuznika, Banka bankaPrimaoca, Nalog primljenNalog) {
		MT103 mt103 = new MT103();

		mt103.setIdPoruke((UUID.randomUUID().toString()));
		mt103.setSwiftKodBankeDuznika(bankaDuznika.getSwiftKod());
		mt103.setObracunskiRacunBankeDuznika(bankaDuznika.getObracunskiRacun().getBrojRacuna());
		mt103.setSwiftKodBankePoverioca(bankaPrimaoca.getSwiftKod());
		mt103.setObracunskiRacunBankePoverioca(bankaPrimaoca.getObracunskiRacun().getBrojRacuna());
		mt103.setDuznik(primljenNalog.getDuznik());
		mt103.setSvrhaPlacanja(primljenNalog.getSvrhaPlacanja());
		mt103.setPrimalac(primljenNalog.getPrimalac());
		mt103.setDatumNaloga(primljenNalog.getDatumNaloga());
		mt103.setDatumValute(primljenNalog.getDatumValute());
		mt103.setRacunDuznika(primljenNalog.getRacunDuznika());
		mt103.setModelZaduzenja(primljenNalog.getModelZaduzenja());
		mt103.setPozivNaBrojZaduzenja(primljenNalog.getPozivNaBrojZaduzenja());
		mt103.setRacunPoverioca(primljenNalog.getRacunPrimaoca());
		mt103.setModelOdobrenja(primljenNalog.getModelOdobrenja());
		mt103.setPozivNaBrojOdobrenja(primljenNalog.getPozivNaBrojOdobrenja());
		mt103.setIznos(primljenNalog.getIznos());
		mt103.setSifraValute(primljenNalog.getOznakaValute());
		return mt103;
	}

	private PojedinacnoPlacanjeMT102 getPojedinacnoPlacanjeIzNaloga(Nalog primljenNalog) {
		PojedinacnoPlacanjeMT102 placanje = new PojedinacnoPlacanjeMT102();
		placanje.setIdNalogaZaPlacanje(primljenNalog.getIdPoruke());
		placanje.setDuznik(primljenNalog.getDuznik());
		placanje.setSvrhaPlacanja(primljenNalog.getSvrhaPlacanja());
		placanje.setPrimalac(primljenNalog.getPrimalac());
		placanje.setDatumNaloga(primljenNalog.getDatumNaloga());
		placanje.setRacunDuznika(primljenNalog.getRacunDuznika());
		placanje.setModelZaduzenja(primljenNalog.getModelZaduzenja());
		placanje.setPozivNaBrojZaduzenja(primljenNalog.getPozivNaBrojZaduzenja());
		placanje.setRacunPoverioca(primljenNalog.getRacunPrimaoca());

		placanje.setModelOdobrenja(primljenNalog.getModelOdobrenja());
		placanje.setPozivNaBrojOdobrenja(primljenNalog.getPozivNaBrojOdobrenja());
		placanje.setIznos(primljenNalog.getIznos());
		placanje.setSifraValute(primljenNalog.getOznakaValute());

		return placanje;
	}

	private ZaglavljeMT102 getZaglavljeMT102IzNaloga(Banka bankaDuznika, Banka bankaPrimaoca, Nalog primljenNalog) {
		ZaglavljeMT102 zaglavlje = new ZaglavljeMT102();
		zaglavlje.setIdPoruke((UUID.randomUUID().toString()));
		zaglavlje.setSwiftKodBankeDuznika(bankaDuznika.getSwiftKod());
		zaglavlje.setObracunskiRacunBankeDuznika(bankaDuznika.getObracunskiRacun().getBrojRacuna());
		zaglavlje.setSwiftKodBankePoverioca(bankaPrimaoca.getSwiftKod());
		zaglavlje.setObracunskiRacunBankePoverioca(bankaPrimaoca.getObracunskiRacun().getBrojRacuna());

		zaglavlje.setUkupanIznos(primljenNalog.getIznos());
		zaglavlje.setSifraValute(primljenNalog.getOznakaValute());
		zaglavlje.setDatumValute(primljenNalog.getDatumValute());

		return zaglavlje;
	}

	@PayloadRoot(namespace = NAMESPACE_URI2, localPart = "getMT910Request")
	@ResponsePayload
	public GetMT910Response getMT910Request(@RequestPayload GetMT910Request request) {
		GetMT910Response response = new GetMT910Response();
		if(request == null || !ValidacijaSema.validirajSemu(request, "mt910")) {
			System.out.println("Nevalidan dokument ili nije poslat odgovor");
			return null;
		}
		MT910 mt910 = request.getMT910();
		// MT103 mt103 = request.getMT103();

		System.out.println("MT910 idPorukeNaloga od MT103" + mt910.getIdPorukeNaloga());
		MT910Service.save(mt910);

		MT910 mtResponse = new MT910();
		response.setMT910(mtResponse);
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI4, localPart = "getMT103Request")
	@ResponsePayload
	public GetMT910Response getMT103Request(@RequestPayload GetMT103Request request) {
		GetMT910Response response = new GetMT910Response();
		if(request == null || !ValidacijaSema.validirajSemu(request, "mt103")) {
			System.out.println("Nevalidan dokument ili nije poslat odgovor");
			return null;
		}
		try {
			MT910 mt910 = MT910Service.findByidPorukeNaloga(request.getMT103().getIdPoruke());

			System.out.println("Nasao mt910" + mt910.getIdPoruke());
			MT103 mt103 = request.getMT103();

			String brojRacunaPoverioca = mt103.getRacunPoverioca();
			Racun racunPoverioca = racunService.findByBrojRacuna(brojRacunaPoverioca);

			BigDecimal iznos = mt910.getIznos();
			racunPoverioca.setTrenutnoStanje(racunPoverioca.getTrenutnoStanje().add(iznos));
			racunService.save(racunPoverioca);

			Nalog nalog = getNalogIzMT103(mt103);
			nalog.setObradjen(true);
			nalogService.save(nalog);

		} catch (Exception e) {
			System.out.println("Nema MT910");
		}

		MT910 mtResponse = new MT910();
		response.setMT910(mtResponse);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI5, localPart = "getMT102Request")
	@ResponsePayload
	public GetMT910Response getMT102Request(@RequestPayload GetMT102Request request) {
		GetMT910Response response = new GetMT910Response();
		
		if(!ValidacijaSema.validirajSemu(request, "mt102")) {
			System.out.println("Nevalidan mt102 dokument");
			return null;
		}

		try {
			MT910 mt910 = MT910Service.findByidPorukeNaloga(request.getMT102().getZaglavljeMT102().getIdPoruke());
			MT102 mt102 = request.getMT102();
			for (PojedinacnoPlacanjeMT102 pojedinacniNalog : mt102.getPojedinacnoPlacanjeMT102()) {
				Nalog nalog = getNalogIzPojedinanogPlacanja(pojedinacniNalog);
				nalog.setObradjen(true);
				nalogService.save(nalog);
			}
		} catch (Exception e) {
			System.out.println("Nema MT910");
		}

		MT910 mtResponse = new MT910();
		response.setMT910(mtResponse);
		return response;
	}

	private Nalog getNalogIzPojedinanogPlacanja(PojedinacnoPlacanjeMT102 pojedinacno) {
		Nalog nalog = new Nalog();
		nalog.setDatumNaloga(pojedinacno.getDatumNaloga());
		//nalog.setDatumValute(mt103.getDa`);
		nalog.setDuznik(pojedinacno.getDuznik());
		nalog.setHitno(false);
		nalog.setIznos(pojedinacno.getIznos());
		nalog.setModelOdobrenja(pojedinacno.getModelOdobrenja());
		nalog.setModelZaduzenja(pojedinacno.getModelZaduzenja());
		nalog.setOznakaValute(pojedinacno.getSifraValute());
		nalog.setPozivNaBrojOdobrenja(pojedinacno.getPozivNaBrojOdobrenja());
		nalog.setPozivNaBrojZaduzenja(pojedinacno.getPozivNaBrojZaduzenja());
		nalog.setPrimalac(pojedinacno.getPrimalac());
		nalog.setRacunDuznika(pojedinacno.getRacunDuznika());
		nalog.setRacunPrimaoca(pojedinacno.getRacunPoverioca());
		nalog.setSvrhaPlacanja(pojedinacno.getSvrhaPlacanja());
		return nalog;
	}
	
	private Nalog getNalogIzMT103(MT103 mt103) {
		Nalog nalog = new Nalog();
		nalog.setIdPoruke((UUID.randomUUID().toString()));
		nalog.setDuznik(mt103.getDuznik());
		nalog.setSvrhaPlacanja(mt103.getSvrhaPlacanja());
		nalog.setPrimalac(mt103.getPrimalac());
		nalog.setDatumNaloga(mt103.getDatumNaloga());
		nalog.setDatumValute(mt103.getDatumValute());
		nalog.setRacunDuznika(mt103.getRacunDuznika());
		nalog.setModelZaduzenja(mt103.getModelZaduzenja());
		nalog.setPozivNaBrojZaduzenja(mt103.getPozivNaBrojZaduzenja());
		nalog.setRacunPrimaoca(mt103.getRacunPoverioca());
		nalog.setModelOdobrenja(mt103.getModelOdobrenja());
		nalog.setPozivNaBrojOdobrenja(mt103.getPozivNaBrojOdobrenja());
		nalog.setIznos(mt103.getIznos());
		nalog.setOznakaValute(mt103.getSifraValute());
		nalog.setHitno(true);
		return nalog;
	}

	int velicinaStranice = 4;

	@PayloadRoot(namespace = NAMESPACE_URI3, localPart = "getZahtevZaDobijanjeIzvodaRequest")
	@ResponsePayload
	public GetPresekResponse getZahtevZaDobijanjeIzvodaRequest(
			@RequestPayload GetZahtevZaDobijanjeIzvodaRequest request) {
		GetPresekResponse response = new GetPresekResponse();
		Presek presek = new Presek();		
		
		Date datum = request.getZahtevZaDobijanjeIzvoda().getDatum();
		String brRacuna = request.getZahtevZaDobijanjeIzvoda().getBrojRacuna();
		int stranica = request.getZahtevZaDobijanjeIzvoda().getRedniBrojPreseka().intValue();
		Banka banka = getCurrentBank(brRacuna);

		//to do zaglavlje staro stanje
		NaloziZaglavlje nz = getNalogeZaBankuDanIRacun(banka, datum, brRacuna);
		ZaglavljePreseka zaglavlje = nz.zaglavlje;
		zaglavlje.setPrethodnoStanje(getStanjeRacunaZaDatum(datum, brRacuna));
		zaglavlje.setNovoStanje(zaglavlje.getPrethodnoStanje().add(zaglavlje.getUkupnoUKorist()).subtract(zaglavlje.getUkupnoNaTeret()));
		List<Nalog> nalozi = nz.lista;
		List<Nalog> stranicaNaloga = null;
		
		//ako nema za tu stranicu
		int start = velicinaStranice*(stranica-1);
		
		if(nalozi.size() < start)
			stranicaNaloga = new ArrayList<>();
		else if(nalozi.size() < start+4)
			stranicaNaloga = nalozi.subList(start, nalozi.size());
		else 
			stranicaNaloga = nalozi.subList(start, start+velicinaStranice);
		
		for(int i =0;i<stranicaNaloga.size();i++) {
			//promeni !,izbaci iz fora ! i u 471 ista stvar
			StavkaPreseka stavka = setStavkaNalogaIzNaloga(stranicaNaloga.get(i));
			if(stranicaNaloga.get(i).getRacunDuznika().equals(brRacuna))
				stavka.setSmer("I");
			else
				stavka.setSmer("U");

			presek.getStavkaPreseka().add(stavka);
		}
		zaglavlje.setBrojPreseka(request.getZahtevZaDobijanjeIzvoda().getRedniBrojPreseka());
		presek.setZaglavljePreseka(zaglavlje);
		response.setPresek(presek);
		if(!ValidacijaSema.validirajSemu(response,"presek"))
			return null;
		return response;
	}
	
	private StavkaPreseka setStavkaNalogaIzNaloga(Nalog nalog) {
		StavkaPreseka stavka = new StavkaPreseka();
		stavka.setPozivNaBrojOdobrenja(nalog.getPozivNaBrojOdobrenja());
		stavka.setPozivNaBrojZaduzenja(nalog.getPozivNaBrojZaduzenja());
		stavka.setPrimalac(nalog.getPrimalac());
		stavka.setRacunDuznika(nalog.getRacunDuznika());
		stavka.setRacunPrimaoca(nalog.getRacunPrimaoca());
		stavka.setSvrhaPlacanja(nalog.getSvrhaPlacanja());
		stavka.setModelOdobrenja(nalog.getModelOdobrenja());
		stavka.setModelZaduzenja(nalog.getModelZaduzenja());
		stavka.setPozivNaBrojOdobrenja(nalog.getPozivNaBrojOdobrenja());
		stavka.setPozivNaBrojZaduzenja(nalog.getPozivNaBrojZaduzenja());
		stavka.setIznos(nalog.getIznos());
		stavka.setDuznik(nalog.getDuznik());
		stavka.setDatumValute(nalog.getDatumValute());
		stavka.setDatumNaloga(nalog.getDatumNaloga());
		return stavka;
	}

	private Banka getCurrentBank(String brRacuna) {
		List<Banka> banke = bankaService.findAll();
		for (Banka banka : banke) {
			if(brRacuna.contains(banka.getKodBanke()))
				return banka;
		}
		return null;
	}
	
	private BigDecimal getStanjeRacunaZaDatum(Date datum, String brRacuna) {
		List<Banka> banke = bankaService.findAll();
		Racun racun = null;
		for (Banka banka : banke) {
			for(Racun racunDB : banka.getRacuni()) {
				if(racunDB.getBrojRacuna().equals(brRacuna))
					racun = racunDB;
			}
		}
		BigDecimal trenutnoNaRacunu = racun.getTrenutnoStanje();
		List<Nalog> naloziUBazi = nalogService.findAll();
		for (Nalog nalogUBazi : naloziUBazi) {
			if(datum.before(nalogUBazi.getDatumNaloga()) && !nalogUBazi.isObradjen()) {
				if (nalogUBazi.getRacunDuznika().equals(brRacuna)) 
					trenutnoNaRacunu.subtract(nalogUBazi.getIznos());
				else
					trenutnoNaRacunu.add(nalogUBazi.getIznos());
			}
		}
		
		return trenutnoNaRacunu;
	}

	private NaloziZaglavlje getNalogeZaBankuDanIRacun(Banka banka, Date datum, String brRacuna) {
		NaloziZaglavlje nz = new NaloziZaglavlje();
		nz.zaglavlje.setBrojRacuna(brRacuna);
		nz.zaglavlje.setDatumNaloga(datum);
		List<Nalog> naloziUBazi = nalogService.findAll();
		for (Nalog nalogUBazi : naloziUBazi) {
			if (!nalogUBazi.isObradjen() && nalogUBazi.getDatumNaloga().compareTo(datum) == 0)
				if (nalogUBazi.getRacunDuznika().equals(brRacuna)|| nalogUBazi.getRacunPrimaoca().equals(brRacuna)) {
					if (nalogUBazi.getRacunDuznika().equals(brRacuna)) {
						nz.zaglavlje.setBrojPromenaNaTeret(nz.zaglavlje.getBrojPromenaNaTeret().add(new BigInteger(String.valueOf(1))));
						nz.zaglavlje.setUkupnoNaTeret(nz.zaglavlje.getUkupnoNaTeret().add(nalogUBazi.getIznos()));
					}
					else {
						nz.zaglavlje.setBrojPromenaUKorist(nz.zaglavlje.getBrojPromenaUKorist().add(new BigInteger(String.valueOf(1))));
						nz.zaglavlje.setUkupnoUKorist(nz.zaglavlje.getUkupnoUKorist().add(nalogUBazi.getIznos()));
					
					}					
					
					nz.lista.add(nalogUBazi);
				}
		}
		nz.zaglavlje.setNovoStanje(nz.zaglavlje.getUkupnoUKorist().subtract(nz.zaglavlje.getUkupnoNaTeret()));
		return nz;
	}
	

	@PayloadRoot(namespace = NAMESPACE_URI6, localPart = "getZahtevZaDobijanjeNalogaRequest")
	@ResponsePayload
	public GetZahtevZaDobijanjeNalogaResponse getZahtevZaDobijanjeNalogaRequest(
			@RequestPayload GetZahtevZaDobijanjeNalogaRequest request) {
		
		GetZahtevZaDobijanjeNalogaResponse response = new GetZahtevZaDobijanjeNalogaResponse();
		List<Nalog> nalozi = new ArrayList<Nalog>();
		String brojRacuna = request.getZahtevZaDobijanjeNaloga().getBrojRacuna();
		
		List<Nalog> sviNalozi = nalogService.findAll();
		
		for (Nalog nalog : sviNalozi) {
			if(nalog.getRacunDuznika().equals(brojRacuna) || nalog.getRacunPrimaoca().equals(brojRacuna)){
				nalozi.add(nalog);
			}
		}
		
		response.setNalog(nalozi);
		
		return response;
		
	}

}