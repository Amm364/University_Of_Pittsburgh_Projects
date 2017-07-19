var currentDiv="Home";

function changeColor(x){
  if (x === "Steel Valley"){
    document.getElementById("header").style.backgroundColor = "maroon";
    document.getElementById("footer").style.backgroundColor = "maroon";
    document.getElementById("leftIndex").style.backgroundColor = "maroon";
    var buttons = document.getElementsByClassName("nav-button");
    var i=0;
    for (i=0; i<buttons.length;i++){
      buttons[i].style.backgroundColor = "maroon";
    }
  }
  else if (x === "Pitt"){
    document.getElementById("header").style.backgroundColor = "#0000cd";
    document.getElementById("footer").style.backgroundColor = "#0000cd";
    document.getElementById("leftIndex").style.backgroundColor = "#0000cd";
    var buttons = document.getElementsByClassName("nav-button");
    var i=0;
    for (i=0; i<buttons.length;i++){
      buttons[i].style.backgroundColor = "#0000cd";
    }
  }
  else if (x === "Steelers"){
    document.getElementById("header").style.backgroundColor = "black";
    document.getElementById("footer").style.backgroundColor = "black";
    document.getElementById("leftIndex").style.backgroundColor = "black";
    var buttons = document.getElementsByClassName("nav-button");
    var i=0;
    for (i=0; i<buttons.length;i++){
      buttons[i].style.backgroundColor = "black";
    }
  }
}

function greetingsText(x){
  if (x==="matt" || x==="Matt"){
    document.getElementById("nameForm").style.display = "none";
    document.getElementById("revealName").innerHTML = "Hello " + x + "! You are the best professor at Pitt! Welcome to my site!";
    document.getElementById("revealName").style.display = "block";
  }
  else {
    document.getElementById("nameForm").style.display = "none";
    document.getElementById("revealName").innerHTML = "Hello " + x + "! Welcome to my site!";
    document.getElementById("revealName").style.display = "block";
  }
}

function navigation(choice){
  document.getElementById(currentDiv).style.display = "none";
  document.getElementById(choice).style.display = "block";
  currentDiv=choice;
}

function useAjax() {
  $.ajax({
    type: "POST",
    url: "dataUpdate.php",
    data: $('.check:checked').serialize(),
    success:  function(data){
      $("#Poll").hide();
      $("#resultText").html(data);
      $("#Results").show();
    }
  });
}