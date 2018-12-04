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
	myGrid.setHeader("Row number,A,B,C,D,Result1,Result2,Result3");
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
	// var numbers = /^[0-9.]+$/; // only for positive
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
	var columns = [];
	

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
	
	for(var i = 1; i < myGrid.getColumnsNum(); i++)
		{
		columns[i - 1] = myGrid.getColLabel(i);
		}
	
	var formula1 = document.getElementById('formula1').value;
	var formula2 = document.getElementById('formula2').value;
	var formula3 = document.getElementById('formula3').value;
	
	if(formula1 == "" && formula2 == "" && formula3 == ""){
		alert ("Please input at least 1 formula.");
		return;
	}
	
	var obj = {
		'json' :items, columns, formula1, formula2, formula3
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
	$("#divConfig").css({height:"350px", overflow:"auto"});
	// Get the modal
	var modal = document.getElementById('myModal');

	// Get the button that opens the modal
	var btn = document.getElementById("config");

	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("close")[0];

	// When the user clicks the button, open the modal
	btn.onclick = function() {
		modal.style.display = "block";
	}

	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
		modal.style.display = "none";
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	}
	
	// myGrid.setColumnLabel(1,"New Column Label");
}

function onSubmitConfig(){
	var rowsNum = document.getElementById("rowsNum").value;
	var colName1 = document.getElementById("colName1").value;
	var colName2 = document.getElementById("colName2").value;
	var colName3 = document.getElementById("colName3").value;
	var colName4 = document.getElementById("colName4").value;
	var colName5 = document.getElementById("colName5").value;
	var colName6 = document.getElementById("colName6").value;
	var colName7 = document.getElementById("colName7").value;
	
	var newColumnNames = [colName1, colName2, colName3, colName4, colName5, colName6, colName7];
	
	var colNum=myGrid.getColumnsNum();
	
	for(var i=1;i<colNum;i++){
		if(newColumnNames[i-1]!="")
			myGrid.setColumnLabel(i,newColumnNames[i-1]);
	}
	
	var btnSubmit = document.getElementById("submitConfig");
	
	/*btnSubmit.onclick = function() {
		modal.style.display = "block";
	}*/
	
	for(var i = 0; i<rowsNum ;i++){
		myGrid.addRow(i + myGrid.getRowsNum() + 1, [ i + myGrid.getRowsNum() + 1, '', '', '', '', '', '' ], -1)
	}
}