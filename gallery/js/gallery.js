getPicList();

var pictureList;
var index = 0;

function getPicList() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var json = JSON.parse(this.responseText);
            pictureList = json;
            index = 0;
            if (pictureList.length > 0) {
                getPicInfo();
                document.getElementById("count").innerHTML = `Picture: ${index + 1} / ${pictureList.length}`;
            }
            else {
                document.getElementById("caption").innerHTML = 'No picture';
            }
        }
    }
    xhttp.open('GET', `/gallery/gallery?action=getList&phase=${document.getElementById('searchField').value}`, true);
    xhttp.send();
}

function getPicInfo() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var json = JSON.parse(this.responseText);

            document.getElementById("picture").src = `./images/${pictureList[index]}`;
            document.getElementById("fileName").innerHTML = json.fileName;
            document.getElementById("caption").innerHTML = json.caption;
            document.getElementById("date").innerHTML = json.date;
            document.getElementById("count").innerHTML = `Picture: ${index + 1} / ${pictureList.length}`;
        }
    }
    xhttp.open('GET', `/gallery/gallery?action=getPicInfo&photoid=${pictureList[index]}`, true);
    xhttp.send();
}

function nextPic() {
    index === pictureList.length - 1 ? index = 0 : index++;
    getPicInfo();
}


function prevPic() {
    index === 0 ? index = pictureList.length - 1 : index--;
    getPicInfo();
}

function autoPlay() {
    setInterval(nextPic, 6000);
}

document.getElementById('searchField').addEventListener('keypress', function (event) {
    if (event.keyCode == 13) {
        event.preventDefault();
        getPicList();
    }
});