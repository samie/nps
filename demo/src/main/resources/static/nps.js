
function importNpsElement() {
    
    // Import web component if needed
    var npsImport = document.head.querySelector("#nps-element-import");
    if (!npsImport) {
        npsImport = document.createElement("script");
        npsImport.id = "nps-element-import";
        npsImport.type = "module";
        npsImport.src ="./web-component/nps-feedback.js"
        document.head.appendChild(npsImport);
    }

}

function importNpsPopupCss() {

        // Create and append style element
        var style = document.head.querySelector("#nps-css");
        if (!style) {
            style = document.createElement('link');
            style.id = "nps-css";
            style.rel = "stylesheet";
            style.href = "nps.css";
            document.head.appendChild(style);                        
        }
}

function createNpsPopup(product, header, question) {

    // Create popup and append
    const popupHTML = `
        <div id="nps-popup">
            <span id="nps-close">&times;</span>
            <nps-feedback id="nps-widget"></nps-feedback>
        </div>`;
    document.body.insertAdjacentHTML('beforeend', popupHTML);

    // Pass parameters
    const nps = document.body.querySelector('#nps-widget');
    if (nps && product) {
        nps.setAttribute("product",product);
    }    
    if (nps && header) {
        nps.setAttribute("header",header);
    }
    if (nps && question) {
        nps.question = question;
    }

    // Click to open
    const popup = document.getElementById('nps-popup');
    popup.onclick = function(e) {
        const popup = document.getElementById('nps-popup');
        if (popup) popup.classList.add('open');    
        if (e) e.stopPropagation(); 
    }


    // Close button
    const span = document.getElementById('nps-close');
    span.onclick = function(e) {
        const popup = document.getElementById('nps-popup');
        if (popup) popup.classList.remove('open');   
        if (e) e.stopPropagation(); 
    }

};

function openNpsPopup() {
    const popup = document.getElementById('nps-popup');
    if (popup) popup.classList.add('open');    
}

function closeNpsPopup() {
    const popup = document.getElementById('nps-popup');
    if (popup) popup.classList.remove('open');    
}

// Init NPS widget
function initNps() {
    importNpsElement();
    importNpsPopupCss();
}

initNps();

export {createNpsPopup, openNpsPopup, closeNpsPopup};