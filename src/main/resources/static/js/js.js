
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
function toggleSidebar() {
	  
	  $("#sidebar-container").toggleClass( "d-none d-md-block" )
	  
}


function overlay(isShow){
	  var elm = document.getElementById('sidebar-container')
	  if (isShow) {
	    elm.style.display = 'fixed';
	  } else {
	    elm.style.display = 'none';
	  }
	}

	function openNav() {
	  overlay(true);
	  $("#sidebar-container").removeClass( "d-none d-md-block" )
	}

	function closeNav() {
	  overlay(false);
	  $("#sidebar-container").removeClass( "d-none d-md-block" )
}
	function cerrarSidebar(){
		console.log("activo cambio")
		$("#sidebar-container").addClass( "d-none d-md-block" );
	}
	
$(function() {
	
	$(".selectpicker").selectpicker("setStyle", "default-style", "add");
	
	document.addEventListener('touchstart', handleTouchStart, false);        
	document.addEventListener('touchmove', handleTouchMove, false);

	var xDown = null;                                                        
	var yDown = null;

	function getTouches(evt) {
	  return evt.touches ||             // browser API
	         evt.originalEvent.touches; // jQuery
	}                                                     

	function handleTouchStart(evt) {
	    const firstTouch = getTouches(evt)[0];                                      
	    xDown = firstTouch.clientX;                                      
	    yDown = firstTouch.clientY;                                      
	};                                                
	
	function handleTouchMove(evt) {
	    if ( ! xDown || ! yDown ) {
	        return;
	    }

	    var xUp = evt.touches[0].clientX;                                    
	    var yUp = evt.touches[0].clientY;

	    var xDiff = xDown - xUp;
	    var yDiff = yDown - yUp;

	    if ( Math.abs( xDiff ) > Math.abs( yDiff ) ) {/*most significant*/
	        if ( xDiff > 10 ) {
	        	$("#sidebar-container").addClass( "d-none d-md-block" );
	        	console.log("se cierra:"+xDiff);
	        } else if(xDiff<-7){
	        	const container = document.getElementById('table');
	        	if(container){
	        		if(container.scrollLeft>0){
	        			
	        		}else{
	        			$("#sidebar-container").removeClass("d-none d-md-block");
	        		}
	        	}
	        	else{
	        		$("#sidebar-container").removeClass("d-none d-md-block");
	        	}
	        	
	        	
	        	console.log("se abre:"+xDiff );
	        }
	    } else {
	        if ( yDiff > 0 ) {
	            /* up swipe */ 
	        } else { 
	            /* down swipe */
	        }                                                                 
	    }
	    /* reset values */
	    xDown = null;
	    yDown = null;                                             
	};
	
});
