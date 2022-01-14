function navAction(){
    let url = document.location;
    let navUl = document.querySelector("nav ul");
    let navUlChildren = navUl.children;
    for (let i =0; i < navUlChildren.length; i++)
    {
        if(String(url).match(navUlChildren[i].id)){
            navUlChildren[i].className = "active";
        }
        else
            delete navUlChildren[i].className;
    }
    if(String(url).match("upload") || String(url).match("result"))
        navUlChildren[1].className = "active";
}