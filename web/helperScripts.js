/*
 * Helper scripts for the Check Exercise Tool front-end.
 */

var menuItems = {
        "Chapter 1" : ["Exercise01_01", "Exercise01_02", "Exercise01_01", "Exercise01_02"],
        "Chapter 2" : ["Exercise02_03", "Exercise02_02", "Exercise02_01", "Exercise02_02"]
};

// populates the drop-down menu for chapters
function buildChapterMenu() {
    for (var chapter in menuItems) {
        document.getElementById("chapterMenu").innerHTML += "<option>" + chapter + "</option>";
    }
}

// updates the exercise drop-down menu options whenever the user selects a new chapter.
function updateMenu() {
    
    var chapter = document.getElementById("chapterMenu").value;
    var items = menuItems[chapter];
    document.getElementById("exerciseMenu").innerHTML = "";
    for (var item in items) {
        document.getElementById("exerciseMenu").innerHTML += "<option>" + menuItems[chapter][item] + "</option>";
    }
}

// sets hidden field value to be equal to the ace text editor's contents
function updateEditorContents() {
    document.getElementById("editorContents").setAttribute('value', editor.getValue());
}
