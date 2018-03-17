(function(win, doc) {
    var docEl = doc.documentElement;
    var appVersion = win.navigator.appVersion;
    var isAndroid = appVersion.match(/android/gi);
    var isIPhone = appVersion.match(/iphone/gi); /*just support safari && chromeif(!isIPhone || (appVersion.indexOf("Safari") < 0 && appVersion.indexOf("Chrome") < 0)){return;}*/
    var devicePixelRatio = win.devicePixelRatio;
    var dpr = 1,
        scale = 1;
    if (isIPhone) {
        if (devicePixelRatio >= 3) {
            dpr = 3;
        } else if (devicePixelRatio >= 2) {
            dpr = 2;
        } else {
            dpr = 1;
        }
    } else {
        dpr = 1;
    }
    scale = 1 / dpr;
    var metaEl = doc.querySelector('meta[name="viewport"]');
    if (!metaEl) {
        metaEl = doc.createElement('meta');
        metaEl.setAttribute('name', 'viewport');
        if (docEl.firstElementChild) {
            docEl.firstElementChild.appendChild(metaEl);
        } else {
            var wrap = doc.createElement('div');
            wrap.appendChild(metaEl);
            doc.write(wrap.innerHTML);
        }
    }
    if (dpr) {
        metaEl.setAttribute('content', 'initial-scale=' + scale + ', maximum-scale=' + scale + ', minimum-scale=' + scale + ', user-scalable=no');
        docEl.style.fontSize = 20 * dpr + 'px';
    }
    window.DPR = dpr;
})(window, document);