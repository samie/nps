
function importCustomElement() {
    
    // Import web component exported by CustomElementExporter
    var npsImport = document.head.querySelector("#exported-element-import");
    if (!npsImport) {
        npsImport = document.createElement("script");
        npsImport.id = "exported-element-import";
        npsImport.type = "module";
        npsImport.src ="./web-component/nps-feedback.js" // This should match the tag
        document.head.appendChild(npsImport);
    }

}

function importNpsPopupCss() {

        // Create and append style element for the popup
        var style = document.head.querySelector("#nps-css");
        if (!style) {
            style = document.createElement('link');
            style.id = "nps-css";
            style.rel = "stylesheet";
            style.href = "nps.css";
            document.head.appendChild(style);                        
        }
}

function createNpsPopup(product, header, question, link, linkText) {

    // Create popup HTML and append to the end of the document
    const popupHTML = `
        <div id="nps-popup">
            <span id="nps-close">&times;</span>
            <nps-feedback id="nps-widget"></nps-feedback>
        </div>`;
    document.body.insertAdjacentHTML('beforeend', popupHTML);

    // Pass parameters
    const nps = document.body.querySelector('#nps-widget');
    if (nps && product) {
        nps.product = product;
    }    
    if (nps && header) {
        nps.header = header;
    }
    if (nps && question) {
        nps.question = question;
    }
    if (nps && link) {
        nps.link = link;
    }
    if (nps && linkText) {
        nps.linktext = linkText;
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
    importCustomElement();
    importNpsPopupCss();
}
initNps();

export {createNpsPopup, openNpsPopup, closeNpsPopup};