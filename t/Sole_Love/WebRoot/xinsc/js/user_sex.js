$(document).ready(function(){
    $("div.nan").click(function(){
        $("img.img1").show();
        $("img.img2").hide();
    })
    $("div.nv").click(function(){
        $("img.img1").hide();
        $("img.img2").show();
    })
})