data = {
	rows : [ {
		id : 1,
		data : [ 1, "Text", "10", "2", "50", "", "", "" ]
	}, {
		id : 2,
		data : [ 2, "Text", "4", "3", "35", "", "", "" ]
	}, {
		id : 3,
		data : [ 3, "Text", "55.55", "66.66", "20", "", "", "" ]
	} ]
};

dhtmlxValidation.isMax10 = function(data) {// data should include less than 10
											// symbols
	return data.length <= 10;
};

var myGrid;
function doOnLoad() {
	myGrid = new dhtmlXGridObject('gridbox');
	myGrid.setHeader("Column number, Data1, Data2, Data3,Data4, Result1, Result2, Result3");
	myGrid.setInitWidths("70,*,*,*,*,*,*,*");
	myGrid.setColAlign("left,left,left,left,left,left,left,left");
	myGrid.setColTypes("ro,edtxt,edn,edn,edn,ro,ro,ro");
	myGrid.setColValidators([ null, "NotEmpty", "NotEmpty", "NotEmpty", "NotEmpty" ]);
	myGrid.setNumberFormat("0,000.00", 2, ".", ",");
	myGrid.setNumberFormat("0,000.00", 3, ".", ",");
	myGrid.setNumberFormat("00.00%", 4, ".", ",");
	myGrid.setColSorting("int,str,str,str,str,str,str");
	myGrid.init();
	myGrid.enableAlterCss("even", "uneven");
	myGrid.enableMultiselect(true);
	myGrid.setColumnColor("#CCE2FE");
	myGrid.enableAutoHeight(true, 350);
	
	myGrid.enableValidation(true);
	
	myGrid.setColValidators("Min4,Max10"); 

	myGrid.parse(data, "json");
	
}

var rowID = 3;

function onAddRow() {
	rowID = rowID + 1;
	myGrid.addRow(rowID, [ rowID, '', '', '', '', '', '' ], -1)
}

function onDeleteRow() {
	myGrid.deleteSelectedItem();
}

function validate(i, j){
	//var numbers = /^[0-9.]+$/; // only for positive
	var numbers  = /^-?\d*\.?\d+$/; // positive and negative numbers
	var text = /^[a-zA-Z]+$/;
	
	var cellValue = myGrid.cellByIndex(i, j).getValue();
	var valueLength = cellValue.length;
	
	switch(true)
	{
	case j==1:
		
		if(cellValue.match(text) && valueLength < 21)
			return true;
	
		alert("Wrong input");
		myGrid.selectCell(i, j);
		myGrid.cellByIndex(i, j).setValue("");

		return false;
		break;
		
	case (j==2 || j==3):
		
		if ((cellValue.match(numbers)) && valueLength < 14)
			return true;
	
		alert("Wrong input");
		myGrid.selectCell(i, j);
		myGrid.cellByIndex(i, j).setValue("");

		return false;
		break;
		
	case j==4:
		
		if ((cellValue.match(numbers)) && valueLength < 10)
			return true;
	
		alert("Wrong input");
		myGrid.selectCell(i, j);
		myGrid.cellByIndex(i, j).setValue("");

		return false;
		break;
	}
}

function onSuccess(data){
	for (var i=0;i<myGrid.getRowsNum();i++){
		var resultInner = data.result[i];
		for(var j=5;j<myGrid.getColumnsNum();j++){
			myGrid.cellByIndex(i, j).setValue(resultInner[j-5]);
		}
	}
}

function onCalculate() {
	var items = [];

	for (var i = 0; i < myGrid.getRowsNum(); i++) {
		items[i] = [];
		for (var j = 1; j < myGrid.getColumnsNum() - 3; j++) {
			
			var cellValue = myGrid.cellByIndex(i, j).getValue();
			
			if (cellValue == "" ) {
				alert("Empty value!");
				myGrid.selectCell(i, j);
				myGrid.editCell();
				return;
			}

			if (!validate(i, j)) {
				return;
			}

			items[i][j - 1] = myGrid.cellByIndex(i, j).getValue();
		}

	}
	
	var formula1 = document.getElementById('formula1').value;
	var formula2 = document.getElementById('formula2').value;
	var formula3 = document.getElementById('formula3').value;
	
	if(formula1 == "" && formula2 == "" && formula3 == ""){
		alert ("Please input at least 1 formula.");
		return;
	}
	
	/*if(!formula1.includes("Data2") && !formula1.includes("Data3") && !formula1.includes("Data4")){
		alert ("Incorect formula1");
		document.getElementById('formula1').value ="";
		return;
	}*/
	
	var obj = {
		'json' : items, formula1, formula2, formula3
	}
	
	var json = JSON.stringify(obj);
	$.ajax({
		url : "/intelligent.grid/grid",
		type : "POST",
		dataType : 'json',
		data : json,
		success: onSuccess,
	    error: function(data) {
	    	alert("Something went wrong!");
	    }
	});
}

function onConfig(){
}