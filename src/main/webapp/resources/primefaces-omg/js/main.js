$(document)
		.ready(
				function() {
					$("#omega-menu-button").click(function() {
						$(".wrapper").toggleClass("sidebar-inactive-l");
					});

					$("#options-menu-button").click(function() {
						$("#topbar-icons").toggleClass("topbar-icons-visible");
					});

					$(".nano").nanoScroller();

					// Footer Fixed or relative
					// $(window).scroll(function(){
					// checkPositionFooter();
					// });
					// checkPositionFooter();
					// function checkPositionFooter(){
					// if($("footer.footer").length < 1) return;
					// console.log($("footer.footer").position().top + " :
					// "+$("footer.footer").height() +" : "+
					// $(window).height());
					// if($("footer.footer").position().top +
					// $("footer.footer").height() < $(window).height()){
					// $("footer.footer").addClass("stay-bottom");
					// }else{
					// $("footer.footer").removeClass("stay-bottom");
					// }
					// }
					//	
					$(".tabview-min button")
							.click(
									function() {
										var htmlLi = "";
										var listItems = $(
												".tabview-wrapper .ui-tabs-nav li")
												.each(
														function(i, e) {
															var selected = (e.className
																	.indexOf("ui-tabs-selected") != -1) ? "#fff"
																	: "#f6f7f9";
															htmlLi += '<li style="background: '
																	+ selected
																	+ '" class="">'
																	+ e.innerHTML
																	+ '</li>';
															// $(".tabview-min
															// .dropdown-menu
															// a").on('click',changeTab(i));
														});
										$(document)
												.delegate(
														"div.tabview-min.open ul li",
														'click',
														function(e) {
															var index = $(
																	"div.tabview-min.open ul li")
																	.index(this);
															changeTab(index);
														});

										$(".tabview-wrapper .dropdown-menu")
												.empty().append(htmlLi);
									});

					$(".tabview-min .dropdown").on("click", "li", function() {
						checkPositionFooter();
					});

					function changeTab(index) {
						PF("tabPanelWidget").select(index);
					}

					// set z index của select one menu neu select one menu nam
					// trong dia log thi de auto, nam ngoai dialog thi de
					// inherit
					/*
					 * $(document).delegate("body .main form>div:not(.ui-dialog)
					 * .form-group .ui-selectonemenu:not(.dialog-selectOneMenu)
					 * ",'click',function(e){
					 * $(".ui-selectonemenu-panel").css({"z-index":"inherit"});
					 * $(".ui-selectonemenu-panel").css({"visibility":"visible"});
					 * });
					 */

					$(document).delegate(
							"div.ui-dialog-content.ui-widget-content",
							'mousewheel', function(e) {
								$(".ui-selectonemenu-panel").css({
									"visibility" : "hidden"
								});
							});

					$(document).delegate("body  .main .ui-selectonemenu",
							'click', function(e) {
								$(".ui-selectonemenu-panel").css({
									"visibility" : "visible"
								});
							});
					
					$(document).delegate("body  .main .ui-selectcheckboxmenu",
							'click', function(e) {
								$(".ui-selectcheckboxmenu-panel").css({
									"visibility" : "visible"
								});
							});

					// set visibility ui-autocomplete-input when key press
					$(document)
							.delegate(
									"body  .main form>div .form-group .ui-autocomplete-input ",
									'keydown', function(e) {
										$(".ui-autocomplete-panel").css({
											"visibility" : "visible"
										});
									});

					// set visibility ui-autocomplete-input when srcoll page
					$(document).scroll(function() {
						$(".ui-autocomplete-panel").css({
							"visibility" : "hidden"
						});
						$(".ui-selectonemenu-panel").css({
							"visibility" : "hidden"
						});
						$(".ui-selectcheckboxmenu-panel").css({
							"visibility" : "hidden"
						});
					});

					// var prevVal = "";
					// $("form").delegate("[data-regex]", "keyup", function(e){
					// var this$ = $(e.target);
					// var regex = this$.data("regex");
					// var value = this$.val();
					// if(regex != null && regex.length > 0){
					// var test = new RegExp(regex, "gi").test(value);;
					// console.log("Check input "+regex+ " : "+value+ " =
					// "+test);
					// if(!test){
					// // $(this).val(prevVal);
					// this$.parent().addClass("has-error").removeClass("has-success");
					// }else{
					// prevVal = value;
					// this$.parent().removeClass("has-error").addClass("has-success");
					// }
					//		   
					// }
					// e.preventDefault();
					// return false;
					// });

					// $("form").delegate("[data-regex]", "keyup", function(e){
					// var this$ = $(e.target);
					// this$.inputmask();
					// });
				});
// Disallow utf-8 charactor
/*
 * $(".digit-numeric").alphanum({ allowOtherCharSets : false
 * 
 * }); //digit only $(".digit-only").alpha({
 * 
 * }); //numeric only $(".numeric-only").numeric({
 * 
 * });
 */
function showLoading() {
	$("#statusDialog").show();
}
function hideLoading() {
	$("#statusDialog").hide();
}

$(window).load(function() {
	$("#redirectDialog").css({
		'display' : 'none'
	});
});

window.onbeforeunload = function() {
	$("#redirectDialog").css({
		'display' : 'block'
	});
};

window.onload = function() {
	$("#redirectDialog").css({
		'display' : 'none'
	});
};

function hideRedirect() {
	$("#redirectDialog").css({
		'display' : 'none'
	});
}

$(document).ready(function() {
	var $docH = screen.height;
	var $topH = $('.navbar-fixed-top').height() + $('.banner').height();
	var $footH = $('.footer').height()
	console.log($topH + $footH);
	
	$('.wrapper').css({
		"margin-top" : ($topH - 2) + 'px'
	});
	
	// The document height will grow as the content on the page grows.
	$('.main').css({
		/*
		 * The default height of .navbar is 50px with a 1px border, change this
		 * 52 if you change the height of your footer.
		 */
		"min-height" : ($docH - $footH - $topH - 105) + 'px'
	});

	$('.ui-dialog').bind('show', function() {
		alert('beforeShow');
	})
    
    $('.ui-menu-child').each(function() {
    	  var menuHeight = $( this ).height();
    	  if (menuHeight > 500) {
    		  $( this ).css({'max-height':'500px'});
    	  }
    });
});

function hidescroll() {
	$('html, body').css('overflow', 'hidden');
}

function showscroll() {
	$('html, body').css('overflow', 'auto');
}

// Dung chung de in trang cho chuc nang xem tieu chi, sua hieu luc tieu chi, huy
// hieu luc tieu chi cua cac bo quan ly tieu chi
function printXemTC() {
	$(".dialog-fullscreen").css({
		"max-height" : "none",
		"position" : "absolute",
		"overflow-y" : "hidden"
	});
	document.getElementById("formViewTieuchi:outXemTc").style.display = "block";
	PrimeFaces.expressions.SearchExpressionFacade.resolveComponentsAsSelector(
			'formViewTieuchi:outXemTc').jqprint();
	return false;
}

function printSuaHL() {
	$(".dialog-fullscreen").css({
		"max-height" : "none",
		"position" : "absolute",
		"overflow-y" : "hidden"
	});
	document.getElementById("formSuaHL:panelSuaHL").style.display = "block";
	PrimeFaces.expressions.SearchExpressionFacade.resolveComponentsAsSelector(
			'formSuaHL:panelSuaHL').jqprint();
	return false;
}

function printHuyHL() {
	$(".dialog-fullscreen").css({
		"max-height" : "none",
		"position" : "absolute",
		"overflow-y" : "hidden"
	});
	document.getElementById("formHuyHL:panelHuyHL").style.display = "block";
	PrimeFaces.expressions.SearchExpressionFacade.resolveComponentsAsSelector(
			'formHuyHL:panelHuyHL').jqprint();
	return false;
}
// End

function printQlDGL() {
	$(".formPrint").css({
		"max-height" : "none",
		"position" : "absolute",
		"overflow-y" : "hidden",
		"display" : "block",
		"margin" : "0"
	});
	document.getElementById("formDetailGPD:formPrint").style.display = "block";
	PrimeFaces.expressions.SearchExpressionFacade.resolveComponentsAsSelector(
			'formDetailGPD:formPrint').jqprint();
	$(".formPrint").css({
		"display" : "none"
	});
	return false;
}

function keepDialogWhenValidateFail(args, dialogWidgetVar) {
	if (args && !args.validationFailed) {
		PF(dialogWidgetVar).hide();
	}
}

function enterToSubmit(id) {
	document.getElementById(id).click();
}

PrimeFaces.widget.SelectOneMenu.prototype.handleEnterKey = function(event) {
	if (this.panel.is(':visible')) {
		this.selectItem(this.getActiveItem());
		event.preventDefault();
		event.stopPropagation();
	}
}
