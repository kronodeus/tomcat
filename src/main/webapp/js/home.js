window.onload = function() {
	getActiveTransactionInfo(renderPage);
};

const getActiveTransactionInfo = function(callback) {
	const xmlHttp = new XMLHttpRequest();

	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
			callback(xmlHttp.responseText);
	};

	xmlHttp.open("GET", "/transaction", true);
	xmlHttp.send(null);
};

const renderPage = function(transactionInfo) {
	const info = React.createElement("p", null, transactionInfo);
	const link = React.createElement("a", {href: "/transaction"}, "Click here for more info");

	ReactDOM.render(
		info,
		document.getElementById("info")
	);

	ReactDOM.render(
		link,
		document.getElementById("link")
	);
};