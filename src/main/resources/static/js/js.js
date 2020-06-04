
//-------------------------------------
//-----------SIDEBAR----------------
//-------------------------------------
// Collapse/Expand icon
$('#collapse-icon').addClass('fa-angle-double-left'); 

// Collapse click
$('[data-toggle=sidebar-colapse]').click(function() {
    SidebarCollapse();
});

function SidebarCollapse () {
    $('.menu-collapsed').toggleClass('d-none');
    $('.sidebar-submenu').toggleClass('d-none');
    $('.submenu-icon').toggleClass('d-none');
    $('#sidebar-container').toggleClass('sidebar-expanded sidebar-collapsed');
    
    
    var SeparatorTitle = $('.sidebar-separator-title');
    if ( SeparatorTitle.hasClass('d-flex') ) {
        SeparatorTitle.removeClass('d-flex');
    } else {
        SeparatorTitle.addClass('d-flex');
    }
    
    
    $('#collapse-icon').toggleClass('fa-angle-double-left fa-angle-double-right');
}
//-------------------------------------
//-----------CALENDARIO----------------
//-------------------------------------

function anadirInquilino () {
	var x = document.getElementById("botonAnadirInquilino");
	  if (x.style.display === "none") {
	    x.style.display = "block";
	  } else {
	    x.style.display = "none";
	  }
	  
	  var x = document.getElementById("anadirInquilinoExtra");
	  if (x.style.display === "none") {
	    x.style.display = "block";
	  } else {
	    x.style.display = "none";
	  }
}
$(function() {
	
	$(".btn.dropdown-toggle.btn-light").css("border", "1px solid #ced4da");
	$(".btn.dropdown-toggle.btn-light").css("background-color", "#fff");
	$(".dropdown.bootstrap-select").css("width", "100%");
	
//	/*<![CDATA[*/
//	var inquilino=[[${inquilinos}]];
//	var text="";
//	
//	for (var r of inquilino){
//		console.log("<option data-tokens='"+r["nombre"]+" "+r["apellido"]+" "+r["dni"] +"' value='"+r["id"]+"'>"+r["nombre"]+" "+r["apellido"] + "</option>");
//		text+="<option data-tokens='"+r["nombre"]+" "+r["apellido"]+" "+r["dni"] +"' value='"+r["id"]+"'>"+r["nombre"]+" "+r["apellido"] + "</option>";
//	}
//
//
//	
//	
//	
//	document.getElementById("selectInquilinosNavbar").innerHTML =text;
//	console.log(text);
//	/*]]>*/
});
