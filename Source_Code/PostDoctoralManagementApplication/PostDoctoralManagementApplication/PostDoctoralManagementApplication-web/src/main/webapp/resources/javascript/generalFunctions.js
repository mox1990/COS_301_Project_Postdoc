/* 
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */

$("#notice-bar").hover(function(){
   $(this).addClass("notice-bar-hover");
   $(this).removeClass("notice-bar");
   $(".notice-bar-header").hide();
   $(".notice-bar-header-hover").show();
}, function (){
   
   $(this).addClass("notice-bar");
   $(this).removeClass("notice-bar-hover");
   $(".notice-bar-header").show();
   $(".notice-bar-header-hover").hide();
});



function showInfoDiv(divindex)
{
    $('.informationContainer').stop(true, true);
    $("#info" + divindex).slideDown({duration : 250, easing : 'linear'});
}

function hideInfoDiv(divindex)
{
    $('.informationContainer').stop(true, true);
    $("#info" + divindex).hide();
    //$("#info" + divindex).slideUp({duration : 250, easing : 'linear'});
}  

function notificationbarHover()
{
    
}