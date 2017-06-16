var services = angular.module('admin.services', ['ngResource']);

services.service('adminService', ['$http', function($http){
	this.checkRights = function(){
		return $http.get("/firmas/checkRights");
	}	
	
	this.saveZaglavljeFakture = function(zaglavljeFakture){
		return $http.post("/zaglavljeFakture",zaglavljeFakture);
	}
	
	this.saveStavkaFakture = function(stavkaFakture,idZaglavlja){
		return $http.post("/faktura/"+idZaglavlja,stavkaFakture);
	}
	
	this.findAllUlazneFakture = function(){
		return $http.get("/faktura/findAllUlazneFakture");
	}
	
	
	this.findAllPoslovniSaradnici = function(idFirme){
		return $http.get("/firma/findAllPoslovniSaradnici/"+idFirme);
	}
	
	this.createHTML = function(faktura){
		return $http.post("/faktura/createHTML",faktura);
	}
	
	this.createPDF = function(faktura){
		return $http.post("/faktura/createPDF",faktura);
	}
	
	this.sendNalog = function(faktura){
		return $http.post("/nalog/sendNalog",faktura);
	}
	
	this.sendFaktura = function(faktura){
		return $http.post("/faktura/sendFaktura",faktura);
	}
	
	
	this.findPreseke = function(presek){
		return $http.post("/firma/findPresek",presek);
	}
}]);