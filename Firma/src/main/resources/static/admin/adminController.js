var app = angular.module('admin.controllers', []);
var trenutniPresek = 1;

app.controller('adminController', ['$scope','adminService', '$location','$state',
  	function ($scope, adminService, $location,$state) {
		function checkRights() {
			adminService.checkRights().then(
				function (response) {
					if(response.data != "") {
						$scope.admin = response.data;
					}
				}, 
				function (response){
				    $location.path('login');
					alert("Greska");
				}
			);
		}
		checkRights();
		
		$scope.findPreseke = function() {
			zahtev = {};
			zahtev.datum = $("#startDatum").val();
			zahtev.redniBrojPreseka =  trenutniPresek;
			adminService.findPreseke(zahtev).then(
				function (response) {
					trenutniPresek += 1;
					$scope.nalozi = response.data.stavkaPreseka;
				}, 
				function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.saveZaglavljeFakture= function () {
			adminService.saveZaglavljeFakture($scope.zaglavljeFakture).then(
				function(response){
					alert("Sacuvano")
					$state.go("admin.unosStavkeFakture", {});
					$scope.sacuvanoZaglavlje=response.data;
				}, function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.saveStavkaFakture= function () {
			alert($scope.sacuvanoZaglavlje.id)
			adminService.saveStavkaFakture($scope.stavkaFakture,$scope.sacuvanoZaglavlje.id).then(
				function(response){
					var r = confirm("Dodaj jos jednu stavku?");
					if (r == true) {
						$scope.stavkaFakture=null;
						$state.go("admin.unosStavkeFakture", {});
					} else {
						$scope.sacuvanoZaglavlje=null;
						$scope.stavkaFakture=null;
						$state.go("admin.home", {});
						alert(response.data)
						adminService.sendFaktura(response.data).then(
								function(response){
									alert("Poslata je faktura! "+response.data)
								}, function (response){
									alert("Greska");
								}
							);
					}

				}, function (response){
					alert("Greska");
				}
			);
		}
		
		$scope.findAllUlazneFakture = function () {   
			adminService.findAllUlazneFakture().then(
				function(response){
					$scope.allUlazneFakture = response.data;
				}, function (response){
					alert("Greska!");
				}
			);	
		}
		
		$scope.findAllPoslovniSaradnici = function () {
			adminService.findAllPoslovniSaradnici($scope.admin.firma.id).then(
				function(response){
					$scope.allPoslovniSaradnici = response.data;
				}, function (response){
					alert("Greska!");
				}
			);	
		}
		
		$scope.setSelected = function(code,naziv,adresa,pib,racun) {
	        $scope.selected = code;
	        markRow(code);
	        $scope.zaglavljeFakture.nazivKupca = naziv;
	        $scope.zaglavljeFakture.adresaKupca = adresa;
	        $scope.zaglavljeFakture.pibKupca = pib;
	        $scope.zaglavljeFakture.uplataNaRacun = racun;


	    };	
	    function markRow(code) {   
	    	 var rows = document.getElementsByTagName('tr');
		        for(var i=0; i<rows.length; i +=1) {
		          rows[i].className = "";
		        }
		     element = document.getElementById(code);
		     element.setAttribute("class", "selectedRow");
		}
	    
	    $scope.createPDF = function (faktura) {
	    	adminService.createPDF(faktura).then(function(response){
				window.location.href = "/faktura/fakturaPDF";
            },
			function(response){
				alert("Greska kod createPDF");
			})
		}	
	    
	    $scope.createHTML = function (faktura) {
	    	adminService.createHTML(faktura).then(function(response){
				alert("Uspjelo jeee")
				window.location.href = "/faktura/fakturaHTML";
            },
			function(response){
				alert("Greska kod createPDF");
			})
		}	
	    
	    $scope.sendNalog = function (faktura) {
			adminService.sendNalog(faktura).then(function(response){
				alert("Kreiran nalog za slanje banci!");
            },
			function(response){
				alert("Greska kod sendNalog");
			})
		}
	}
	
]);
