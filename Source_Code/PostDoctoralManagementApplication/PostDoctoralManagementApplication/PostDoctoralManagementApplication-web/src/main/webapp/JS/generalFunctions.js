/* 
 * This file is copyrighted to the authors stated below.
 * Any duplication or modifications or usage of the file's contents               
 * that is not approved by the stated authors is prohibited.
 */
function showInfoDiv(divindex)
{
    $('.informationContainer').stop(true, true);
    $("#info" + divindex).slideDown({duration : 250, easing : 'linear'});
}

function hideInfoDiv(divindex)
{
    $('.informationContainer').stop(true, true);
    $("#info" + divindex).slideUp({duration : 250, easing : 'linear'});
}  

