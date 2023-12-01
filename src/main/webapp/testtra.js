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
var designation = document.getElementById("Dropdes");
designation.addEventListener("change", function() {
	    var selectedValue = designation.value;
	    var champTexte = document.getElementById("Designation")
   			if(selectedValue === "AUTRES"){
				   champTexte.style.display="block";
			   }else{
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
function toggleMForm() {
	var table = document.getElementById("tablebd");
    var AddM = document.getElementById("add_mac");
    var form2 = document.getElementById("filtre");
    if (AddM.style.display === "block") {
		form2.style.display = "none";
       AddM.style.display = "none";
        table.style.display = "inline block";
    } else {
        AddM.style.display = "block";
        table.style.display = "none" ;
   }
}; 
function toggleRForm() {
	var table = document.getElementById("tablebd");
    var AddM = document.getElementById("repa");
    var form2 = document.getElementById("filtre");
    if (AddM.style.display === "block") {
		form2.style.display = "none";
       AddM.style.display = "none";
        table.style.display = "inline block";
    } else {
        AddM.style.display = "block";
        table.style.display = "none" ;
   }
};
function toggleCForm() {
	var table = document.getElementById("tablebd");
    var AddC = document.getElementById("comp");
    var form2 = document.getElementById("filtre");
    if (AddC.style.display === "block") {
		form2.style.display = "none";
       AddC.style.display = "none";
        table.style.display = "inline block";
    } else {
        AddC.style.display = "block";
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
function afficherPopupM( j) {
	 var tableau = document.getElementById("tablebd");
	 var ligne = tableau.rows[ j + 1 ];    
	 var valeursColonne = [];
            for (var i = 0; i < ligne.cells.length; i++) {
                var cellule = ligne.cells[i];
                var valeur = cellule.textContent;
                var valeurfn = valeur.replace(/\s/g, "");
                valeursColonne.push(valeurfn);
                }	
        	document.getElementById("popupM").style.display = 'block';
          	document.getElementById("UNSerM").value =valeursColonne[1];
          	document.getElementById("AUNSerM").value =valeursColonne[1];
  			document.getElementById("URefM").value = valeursColonne [2];
 			document.getElementById("UDesM").value = valeursColonne [3];
		 	document.getElementById("UEtatM").value = valeursColonne [4];
  			document.getElementById("UDateEM").value = valeursColonne [5];
  			document.getElementById("locationupt").value = valeursColonne [7];
  			 
}
function afficherPopupC( j) {
	 var tableau = document.getElementById("tablebd");
	 var ligne = tableau.rows[ j + 1 ];    
	 var valeursColonne = [];
            for (var i = 0; i < ligne.cells.length; i++) {
                var cellule = ligne.cells[i];
                var valeur = cellule.textContent;
                var valeurfn = valeur.replace(/\s/g, "");
                valeursColonne.push(valeurfn);
                }	
       		document.getElementById("popupC").style.display = 'block';
         	document.getElementById("UNSerC").value =valeursColonne[1];
         	document.getElementById("AUNSerC").value =valeursColonne[1];         	
 			document.getElementById("URefC").value = valeursColonne [2];
  			document.getElementById("UDesC").value = valeursColonne [3];
  			document.getElementById("UEtatC").value = valeursColonne [4];
  			document.getElementById("UDateEC").value = valeursColonne [5];
  			document.getElementById("UMacC").value = valeursColonne [6];  			
}
function afficherPopupR( j) {
	 var tableau = document.getElementById("tablebd");
	 var ligne = tableau.rows[ j + 1 ];    
	 var valeursColonne = [];
            for (var i = 0; i < ligne.cells.length; i++) {
                var cellule = ligne.cells[i];
                var valeur = cellule.textContent;
                var valeurfn = valeur.replace(/\s/g, "");
                valeursColonne.push(valeurfn);
                }	
       		document.getElementById("popupR").style.display = 'block';
         	document.getElementById("Uid").value =valeursColonne[1];
         	document.getElementById("UDebutR").value =valeursColonne[2];         	
 			document.getElementById("UMateriel").value = valeursColonne [3];
  			document.getElementById("UPblm").value = valeursColonne [4];
  			document.getElementById("URespbl").value = valeursColonne [5];
  			document.getElementById("Usolt").value = valeursColonne [6];
  			document.getElementById("UFinR").value = valeursColonne [7];  			
}
function compinfo(){
	var infocmp =document.getElementById("addinfocmp");
	var form =document.getElementById("bouton");
	var table =document.getElementById("tablebd");
	var des = document.getElementById("DesM").value;
	table.style.display ='none';
	if(des == "PC"){
	if (infocmp.style.display === 'none'){
		infocmp.style.display = 'block';
		form.style.display = 'none';
		}
	else {
		document.getElementById("addinfocmp").style.display = 'none';
		form.style.display = 'block';
	}}
	
}
function infogrp(m){
	var inforep = document.getElementById("info_rep");
	var sermac = document.getElementById("info_"+m).value;
	var refmac = document.getElementById("inforef_"+m).value;
	var desmac = document.getElementById("infodes_"+m).value;
	document.getElementById("Imac").value = sermac;
	document.getElementById("Iref").value = refmac;
	if(desmac === "pc"){
	inforep.style.display = 'block';
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
       document.getElementById("hIRcm").value= responseArray[4];
       document.getElementById("hIRcpu").value= responseArray[5];
       document.getElementById("hIRrom").value= responseArray[6];
       document.getElementById("hIRram").value= responseArray[7];
    	}
    };
    xhr.send();
   }
}
function killinfo(){
	document.getElementById("info_rep").style.display = 'none';
}
function masquerPopup(cibles) {
      if (cibles === 1) {
        document.getElementById("popupM").style.display = 'none';
    } else if (cibles === 2) {
        document.getElementById("popupC").style.display = 'none';
    }}
function masquerPopupR(){
	document.getElementById("popupR").style.display = 'none';
}
function MacForm() {
	var AddR = document.getElementById("repa");
    var AddM = document.getElementById("add_mac");
    var AddC = document.getElementById("comp");
    var table =document.getElementById("tablebd");
    var table2 =document.getElementById("tabletransfert");
		table.style.display ="none";
		table2.style.display ="none";
    	AddC.style.display = "none";
    	AddR.style.display = "none";
    if (AddM.style.display === "none") {
        AddM.style.display = "block";
    } 
}; 
function ComForm() {
	var AddR = document.getElementById("repa");
    var AddC = document.getElementById("comp");
    var AddM = document.getElementById("add_mac");
    var table =document.getElementById("tablebd");
    var table2 =document.getElementById("tabletransfert");
		table.style.display ="none";
		table2.style.display ="none";
    	AddM.style.display = "none";
    	AddR.style.display = "none";
    if (AddC.style.display === "none") {
       AddC.style.display = "block";
	}
}; 
function RepForm() {
    var AddR = document.getElementById("repa");
    var AddC = document.getElementById("comp");
    var AddM = document.getElementById("add_mac");
    var table =document.getElementById("tablebd");
    var table2 =document.getElementById("tabletransfert");
		table.style.display ="none";
		table2.style.display ="none";
    	AddM.style.display = "none";
    	AddC.style.display = "none";
    if (AddR.style.display === "none") {
       AddR.style.display = "block";
	}
}; 

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
        alert("Transfert de la première valeur :" + firstValue);
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
function closeModal() {
  var modal = document.getElementById("lstable");
  modal.style.display = "none";
}


