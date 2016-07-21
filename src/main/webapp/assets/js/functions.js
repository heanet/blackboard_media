var url = ""
function setVideo(pid, purl) {
    url = purl

    //Cleans already selected videos if there's any
    var videos = document.getElementsByClassName('checked')

    for (var i=0; i<videos.length; i++) {
        videos[i].className = 'video unchecked'
    }

    //Marks the selected video as checked
    document.getElementById(pid).className = 'video checked'
}

function getEmbedTag() {
    if( url === "" ) {
        alert('Please select a video')

        return false
    }
    else {
        //Sets code to be returned to the Blackboard content editor
        var tag ='<iframe src="'+url+'&st=1" width="640" height="480" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" webkitAllowFullScreen allowFullScreen></iframe>'
        document.getElementById('embedHtml').value = tag

        return true
    }
}

//JQuery for overlay animation
$(document).ready( function() {

    $('.video').bind('mouseenter', function() {
        $(this).find('.overlay').animate({
            opacity: 1.,
            height: "toggle"},0750, function(){})
    })

    $('.video').bind('mouseleave', function() {
        $(this).find('.overlay').animate({
            opacity: 1.,
            height: "toggle"},0750, function(){})
    })
})
