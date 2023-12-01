// scripts.js
/*var dropdown = document.getElementById("Etat");
dropdown.addEventListener("change", function() {
	    var selectedValue = dropdown.value;
	    var champTexte = document.getElementById("Mac")
   			if(selectedValue === "Active"){
				   champTexte.disabled = false;
			   }else{
				   champTexte.disabled = true;
			   }
});*/
  var AddR = document.getElementById("repa");
  var AddC = document.getElementById("comp");
  var AddM = document.getElementById("add_mac");
  var AddT = document.getElementById("trans");
  var table =document.getElementById("tablebd");
  var filter = document.getElementById("filtre");
  var cmppc =document.getElementById("addcmppc");
  var cmpprinter =document.getElementById("addcmpprinter");
  var btncomp =document.getElementById("bouton");
  var inforeppc = document.getElementById("reppc");
  var inforepprinter = document.getElementById("repprinter");

  
  
var designation = document.getElementById("Dropdes");
designation.addEventListener("change", function() {
	    var selectedValue = designation.value;
	    var champTexte = document.getElementById("Designation")
   			if(selectedValue === "AUTRES"){
				   champTexte.style.display="block";
			   }else{
				   document.getElementById("DesM").value= selectedValue;
				   champTexte.style.display="none";
			   }
});
var solution = document.getElementById("solt");
solution.addEventListener("change", function() {
	    var value = solution.value;
	    var date = document.getElementById("FinR")
   			if(value != ""){
				   date.disabled = false;
			   }else{
				   date.disabled = true;
			   }
});
var startDateInput = document.getElementById("DateAR");
var midDateInput = document.getElementById("DebutR");
var endDateInput = document.getElementById("FinR");
        // Ajouter un gestionnaire d'événement à la date de début
        startDateInput.addEventListener("input", function () {
            // Mettre à jour la propriété min des dates intermédiaires et de fin
            midDateInput.min = startDateInput.value;
            endDateInput.min = startDateInput.value;
        });

        // Ajouter un gestionnaire d'événement à la date intermédiaire
        midDateInput.addEventListener("input", function () {
            // Mettre à jour la propriété max de la date de début et min de la date de fin
            startDateInput.max = midDateInput.value;
            endDateInput.min = midDateInput.value;
        });

        // Ajouter un gestionnaire d'événement à la date de fin
        endDateInput.addEventListener("input", function () {
            // Mettre à jour la propriété max des dates de début et intermédiaire
            startDateInput.max = endDateInput.value;
            midDateInput.max = endDateInput.value;
        });

var UstartDateInput = document.getElementById("UDateAR");
var UmidDateInput = document.getElementById("UDebutR");
var UendDateInput = document.getElementById("UFinR");

        // Ajouter un gestionnaire d'événement à la date de début
        UstartDateInput.addEventListener("input", function () {
            // Mettre à jour la propriété min des dates intermédiaires et de fin
            UmidDateInput.min = UstartDateInput.value;
            UendDateInput.min = UstartDateInput.value;
        });

        // Ajouter un gestionnaire d'événement à la date intermédiaire
        UmidDateInput.addEventListener("input", function () {
            // Mettre à jour la propriété max de la date de début et min de la date de fin
            UstartDateInput.max = UmidDateInput.value;
            UendDateInput.min = UmidDateInput.value;
        });

        // Ajouter un gestionnaire d'événement à la date de fin
        UendDateInput.addEventListener("input", function () {
            // Mettre à jour la propriété max des dates de début et intermédiaire
            UstartDateInput.max = UendDateInput.value;
            UmidDateInput.max = UendDateInput.value;
        });

 
function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// Obtenez la date actuelle
const currentDate = new Date();

// Ajoutez et soustrayez 3 mois à la date actuelle
const threeMonthsAgo = new Date(currentDate);
threeMonthsAgo.setMonth(currentDate.getMonth() - 3);

const threeMonthsLater = new Date(currentDate);
threeMonthsLater.setMonth(currentDate.getMonth() + 3);

// Formatez les dates dans le format AAAA-MM-JJ
const minDate = formatDate(threeMonthsAgo);
const maxDate = formatDate(threeMonthsLater);

// Sélectionnez tous les champs de date sur la page
const dateInputs = document.querySelectorAll('input[type="date"]');

// Définissez les propriétés min et max pour chaque champ de date
dateInputs.forEach(function (input) {
    input.setAttribute("min", minDate);
    input.setAttribute("max", maxDate);
});

function toggleMForm() {
    if (AddM.style.display === "block") {
		filter.style.display = "none";
       AddM.style.display = "none";
        table.style.display = "inline block";
    } else {
        AddM.style.display = "block";
        table.style.display = "none" ;
   }
}; 
function toggleRForm() {
    if (AddR.style.display === "block") {
		filter.style.display = "none";
       AddR.style.display = "none";
        table.style.display = "inline block";
    } else {
        AddR.style.display = "block";
        table.style.display = "none" ;
   }
};
function toggleCForm() {
    if (AddC.style.display === "block") {
		filter.style.display = "none";
       AddC.style.display = "none";
        table.style.display = "inline block";
    } else {
        AddC.style.display = "block";
        table.style.display = "none" ;
   }
}; 
function toggleTForm() {
    if (AddT.style.display === "block") {
       AddT.style.display = "none";
        table.style.display = "inline block";
    } else {
        AddT.style.display = "block";
        table.style.display = "none" ;
   }
};
function togglefiltre() {
    var form = document.getElementById("filtre");
    if (form.style.display === "none") {
       form.style.display = "flex";
    } else {
        form.style.display = "none";
   }
}; 
function afficherPopupM(j) {
    var ligne = table.rows[j + 1];
    var valeursColonne = [];

    for (var i = 0; i < ligne.cells.length; i++) {
        var cellule = ligne.cells[i];
        var valeur = cellule.textContent;
        var valeurTrimmed = valeur.replace(/^\s+|\s+$/g, "");
        var valeurAvecEspaces = valeurTrimmed.replace(/(\s)\s+/g, "$1");
        valeursColonne.push(valeurAvecEspaces);
    }

    document.getElementById("popupM").style.display = 'block';
    document.getElementById("UNSerM").value = valeursColonne[1];
    document.getElementById("AUNSerM").value = valeursColonne[1];
    document.getElementById("URefM").value = valeursColonne[2];
    document.getElementById("UDropdes").value = valeursColonne[3];
    document.getElementById("UEtatM").value = valeursColonne[4];
    document.getElementById("UDateEM").value = valeursColonne[5];
    document.getElementById("locationupt").value = valeursColonne[7];
}

function afficherPopupC( j) {
	 var ligne = table.rows[ j + 1 ];    
	 var valeursColonne = [];
            for (var i = 0; i < ligne.cells.length; i++) {
                var cellule = ligne.cells[i];
                var valeur = cellule.textContent;
                var valeurTrimmed = valeur.replace(/^\s+|\s+$/g, "");
        		var valeurAvecEspaces = valeurTrimmed.replace(/(\s)\s+/g, "$1");
                valeursColonne.push(valeurAvecEspaces);
                }
                alert(valeursColonne);
       		document.getElementById("popupC").style.display = 'block';
         	document.getElementById("UNSerC").value =valeursColonne[1];
         	document.getElementById("AUNSerC").value =valeursColonne[1];         	
 			document.getElementById("URefC").value = valeursColonne [2];
  			document.getElementById("UDesC").value = valeursColonne [3];
  			document.getElementById("UEtatC").value = valeursColonne [5];
  			document.getElementById("UDateEC").value = valeursColonne [6];
  			document.getElementById("UMacC").value = valeursColonne [7];  			
}
function afficherPopupR(j) {
	var idrep = document.getElementById("rowIndex_"+j).value
	 var ligne = table.rows[ j + 1 ];    
	 var valeursColonne = [];
            for (var i = 0; i < ligne.cells.length; i++) {
                var cellule = ligne.cells[i];
                var valeur = cellule.textContent;
                var valeurTrimmed = valeur.replace(/^\s+|\s+$/g, "");
        		var valeurAvecEspaces = valeurTrimmed.replace(/(\s)\s+/g, "$1");
                valeursColonne.push(valeurAvecEspaces);
                }	
                alert(idrep);
       		document.getElementById("popupR").style.display = 'block';
         	document.getElementById("Uid").value =idrep;
         	document.getElementById("UDateAR").value =valeursColonne[0];         	
         	document.getElementById("UDebutR").value =valeursColonne[1];         	
 			document.getElementById("UMateriel").value = valeursColonne [2];
  			document.getElementById("UPblm").value = valeursColonne [4];
  			document.getElementById("URespbl").value = valeursColonne [5];
  			document.getElementById("Usolt").value = valeursColonne [6];
  			document.getElementById("UFinR").value = valeursColonne [7];  			
}
function compinfo(){
	var des = document.getElementById("Dropdes").value;
	table.style.display ='none';
	if(des.startsWith("PC")){
	if (cmppc.style.display === 'none'){
		cmppc.style.display = 'block';
		btncomp.style.display = 'none';
		}
	else  {
		cmppc.style.display = 'none';
		btncomp.style.display = 'block';
	}}if(des.startsWith("IMPRIMANTE")){
	if (cmpprinter.style.display === 'none'){
		cmpprinter.style.display = 'block';
		btncomp.style.display = 'none';
		}
	else  {
		cmpprinter.style.display = 'none';
		btncomp.style.display = 'block';
	}}if(des === "ONDULEUR"){
	if (cmpprinter.style.display === 'none'){
		cmpprinter.style.display = 'block';
		btncomp.style.display = 'none';
		}
	else  {
		cmpprinter.style.display = 'none';
		btncomp.style.display = 'block';
	}}
	
}
function infogrp(m){
	var sermac = document.getElementById("info_"+m).value;
	var refmac = document.getElementById("inforef_"+m).value;
	var desmac = document.getElementById("infodes_"+m).value;
	document.getElementById("Imac").value = sermac;
	document.getElementById("Iref").value = refmac;
	if(desmac.startsWith("PC")){
	inforeppc.style.display = 'block';
	var xhr = new XMLHttpRequest();
    xhr.open("GET", "Info?mac=" + sermac, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var response = xhr.responseText;
             var responseArray = response.split(',');
	   document.getElementById("IRcm").value= responseArray[0];
       document.getElementById("IRcpu").value= responseArray[1];
       document.getElementById("IRrom").value= responseArray[2];
       document.getElementById("IRram").value= responseArray[3];
       document.getElementById("IRalim").value= responseArray[3];
    	}
    };
    xhr.send();
   }	if(desmac.startsWith("IMPRIMANTE")){
	inforepprinter.style.display = 'block';
	var xhr = new XMLHttpRequest();
    xhr.open("GET", "Info?mac=" + sermac, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var response = xhr.responseText;
             var responseArray = response.split(',');
	   document.getElementById("RepToner").value= responseArray[0];
       document.getElementById("RepTambour").value= responseArray[1];
       document.getElementById("RepTete").value= responseArray[2];
       document.getElementById("RepMiroir").value= responseArray[3];
       document.getElementById("RepmemV").value= responseArray[4];
    	}
    };
    xhr.send();
   }
}
function afficherPopupT(j) {
	 var ligne = table.rows[ j + 1 ];    
	 var valeursColonne = [];
            for (var i = 0; i < ligne.cells.length; i++) {
                var cellule = ligne.cells[i];
                var valeur = cellule.textContent;
                var valeurTrimmed = valeur.replace(/^\s+|\s+$/g, "");
        		var valeurAvecEspaces = valeurTrimmed.replace(/(\s)\s+/g, "$1");
                valeursColonne.push(valeurAvecEspaces);
                }	
       		document.getElementById("popupT").style.display = 'block';
         	document.getElementById("UTid").value =valeursColonne[1];
         	document.getElementById("UDateT").value =valeursColonne[2];         	
 			document.getElementById("UTMateriel").value = valeursColonne [3];
  			document.getElementById("UIExp").value = valeursColonne [4];
  			document.getElementById("UmotifT").value = valeursColonne [6];
  			document.getElementById("UIDes").value = valeursColonne [5];
}
function killinfo(){
	inforeppc.style.display = 'none';
	inforepprinter.style.display = 'none';
}
function masquerPopupM() {
        document.getElementById("popupM").style.display = 'none';
    } 
    function masquerPopupC() {
        document.getElementById("popupC").style.display = 'none';
    }
function masquerPopupR(){
        document.getElementById("popupR").style.display = 'none';
}
function masquerPopupT(){
        document.getElementById("popupT").style.display = 'none';
}
function MacForm() {
		table.style.display ="none";
    	AddC.style.display = "none";
    	AddR.style.display = "none";
    	AddT.style.display ="none";
    if (AddM.style.display === "none") {
        AddM.style.display = "block";
    } 
}; 
function ComForm() {
		table.style.display ="none";
    	AddM.style.display = "none";
    	AddR.style.display = "none";
    	AddT.style.display ="none";
    if (AddC.style.display === "none") {
       AddC.style.display = "block";
	}
}; 
function RepForm() {
		table.style.display ="none";
    	AddM.style.display = "none";
    	AddC.style.display = "none";
    	AddT.style.display ="none";
    if (AddR.style.display === "none") {
       AddR.style.display = "block";
	}
}; 
function transfert(machine , local){
		document.getElementById("lstable").style.display="none";
		table.style.display= "none";
		AddT.style.display = "block";
		document.getElementById("TMateriel").value = machine;
		document.getElementById("IExp").value = local;
}
function remplirTableau(data) {

document.getElementById("lstable").style.display="block";
var table = document.getElementById("tablels");

// Effacez le contenu actuel du tableau
table.innerHTML = "";

// Ajouter une ligne d'en-tête
var headerRow = table.insertRow(0);

// Utilisez un ensemble pour suivre les en-têtes déjà ajoutés
var addedHeaders = new Set();

// Bouclez à travers les données JSON et remplissez le tableau
for (var i = 0; i < data.length; i++) {
    var row = table.insertRow(-1); // Ajouter une nouvelle ligne à la fin du tableau

    for (var key in data[i]) {
        if (!addedHeaders.has(key)) {
            // Si l'en-tête n'a pas déjà été ajouté, ajoutez-le à la ligne d'en-tête
            var headerCell = headerRow.insertCell(-1);
            headerCell.innerHTML = key;
            addedHeaders.add(key);
        }

        // Ajouter la valeur dans la cellule correspondante
        var cell = row.insertCell(-1);
        cell.innerHTML = data[i][key];
    }
 function TransferFunction(dataItem) {
        return function () {
 		var parsedData = JSON.parse(JSON.stringify(dataItem));
        var firstValue = parsedData[Object.keys(parsedData)[0]];
        var location = parsedData[Object.keys(parsedData)[6]];
		transfert(firstValue , location);
        };
    }
var transferCell = row.insertCell(-1);
    var transferButton = document.createElement("button");
    transferButton.innerHTML = "Transferer";
    transferButton.classList.add("btn", "btn-secondary"); 
    transferButton.addEventListener("click",TransferFunction(data[i]))
    transferCell.appendChild(transferButton)}
}

function macliste(n) {
    var agences = document.getElementById("agname"+n).value;
   // alert("ok");
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "Agences?agence=" + agences, true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var data = JSON.parse(xhr.responseText);
               remplirTableau(data);
            }
        };
        xhr.send();
    }
function repa(m){
    var daterep = document.getElementById("date_"+m).value;
	var sermac = document.getElementById("info_"+m).value;
	var refmac = document.getElementById("inforef_"+m).value;
	var xhr1 = new XMLHttpRequest();
        xhr1.open("GET", "Reparation?serie=" + sermac, true);
        xhr1.onreadystatechange = function() {
            if (xhr1.readyState === 4 && xhr1.status === 200) {
                var data = xhr1.responseText.trim();
					if(data.startsWith("PC")){
					document.getElementById("Ipc").value = sermac;
					document.getElementById("I_pc").value = sermac;
					document.getElementById("Irefpc").value = refmac;
					document.getElementById("datepc").value = daterep;
						pcrepa(sermac);
					}
					if(data.startsWith("IMPRIMANTE")){
					document.getElementById("Iprinter").value = sermac;
					document.getElementById("I_printer").value = sermac;
					document.getElementById("Irefprinter").value = refmac;
					document.getElementById("dateprinter").value = daterep;
						printerrepa(sermac);
					}
  		 }
        };
     xhr1.send();
}
function closeModal() {
  var modal = document.getElementById("lstable");
  modal.style.display = "none";
}
function pcrepa(mac){
	inforeppc.style.display = 'block';
	var serie = new XMLHttpRequest();
  	serie.open("GET", "Info?rep=" + mac, true);
   	serie.onreadystatechange = function() {
        	if (serie.readyState === 4 && xhr.status === 200) {
           		var response = serie.responseText;
            	var responseArray = response.split(',');
            	alert(response);
	  			document.getElementById("SRepCm").value= responseArray[0];
      			document.getElementById("SRepCpu").value= responseArray[1];
       			document.getElementById("SRepmem").value= responseArray[2];
     	 		document.getElementById("SRepRam").value= responseArray[3];
     	 		document.getElementById("SRepalim").value= responseArray[3];
    		}
   	};
	var xhr = new XMLHttpRequest();
  	xhr.open("GET", "Info?mac=" + mac, true);
   	xhr.onreadystatechange = function() {
        	if (xhr.readyState === 4 && xhr.status === 200) {
           		var response = xhr.responseText;
            	var responseArray = response.split(',');
	  			document.getElementById("RepCm").value= responseArray[0];
      			document.getElementById("RepCpu").value= responseArray[1];
       			document.getElementById("Repmem").value= responseArray[2];
     	 		document.getElementById("RepRam").value= responseArray[3];
     	 		document.getElementById("Repalim").value= responseArray[4];
     	 		document.getElementById("SRepCm").value= responseArray[5];
      			document.getElementById("SRepCpu").value= responseArray[6];
       			document.getElementById("SRepmem").value= responseArray[7];
     	 		document.getElementById("SRepRam").value= responseArray[8];
     	 		document.getElementById("SRepalim").value= responseArray[9];
    		}
   	};
  	xhr.send();
}
function printerrepa(mac){
	inforepprinter.style.display = 'block';
	var xhr = new XMLHttpRequest();
  	xhr.open("GET", "Info?mac=" + mac, true);
   	xhr.onreadystatechange = function() {
        	if (xhr.readyState === 4 && xhr.status === 200) {
           		var response = xhr.responseText;
            	var responseArray = response.split(',');
	  			document.getElementById("RepToner").value= responseArray[0];
      			document.getElementById("RepTambour").value= responseArray[1];
       			document.getElementById("RepTete").value= responseArray[2];
     	 		document.getElementById("RepMiroir").value= responseArray[3];
     	 		document.getElementById("RepmemV").value= responseArray[4];
      			document.getElementById("SRepToner").value= responseArray[5];
       			document.getElementById("SRepTambour").value= responseArray[6];
     	 		document.getElementById("SRepTete").value= responseArray[7];
     	 		document.getElementById("SRepMiroir").value= responseArray[8];
     	 		document.getElementById("SRepmemV").value= responseArray[9];
    		}
   	};
 	xhr.send();
}
function reparation(){
		table.style.display ="none";
    	AddC.style.display = "none";
    	AddM.style.display = "none";
    	inforeppc.style.display = "none";
    	inforepprinter.style.display = "none";
    	AddR.style.display = "block";

	var sermac = document.getElementById("Imac").value;
	alert(sermac)
   document.getElementById("Materiel").value = sermac;
	
}
function clearInputs() {
    var inputElements = document.querySelectorAll('input');
    var textareaelem = document.querySelectorAll('textarea');
    for (var i = 0; i < inputElements.length; i++) {
        inputElements[i].value = '';
    }for (var i = 0; i < textareaelem.length; i++) {
        textareaelem[i].value = '';
    }
}

