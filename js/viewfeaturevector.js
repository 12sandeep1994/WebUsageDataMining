Ext.require([ 'Ext.grid.*', 'Ext.data.*', 'Ext.form.*',
		'Ext.layout.container.Column', 'Ext.tab.Panel' ]);
Ext.Loader.setConfig({
	enabled : true
});
Ext.tip.QuickTipManager.init();
var reviewColumns = [

{
	header : 'Feature Vector ID',
	dataIndex : 'featureId',
	sortable : true,
	id : 'featureId',
	name : 'featureId',
	width : 100
},
{
	header : 'Token Name',
	dataIndex : 'tokenName',
	sortable : true,
	id : 'tokenName',
	name : 'tokenName',
	width : 100
},

{
	header : 'Feature Vector',
	dataIndex : 'featureVector',
	sortable : true,
	id : 'featureVector',
	name : 'featureVector',
	width : 200
},
{
	header : 'IDFT',
	dataIndex : 'idft',
	sortable : true,
	id : 'idft',
	name : 'idft',
	width : 200
},
{
	header : 'No of URls',
	dataIndex : 'noOfReviews',
	sortable : true,
	id : 'noOfReviews',
	name : 'noOfReviews',
	width : 200
},

{
	header : 'Query',
	dataIndex : 'query',
	sortable : false,
	id : 'query',
	name : 'query',
	width : 100,
	renderer : function(value, metadata, record, rowIndex, colIndex, store) {
		metadata.tdAttr = 'data-qtip="' + value + '"';
		return value;
	}

}, {
	header : 'User ID',
	dataIndex : 'userId',
	sortable : true,
	id : 'userId',
	name : 'userId',
	width : 100
}, {
	header : 'URL',
	dataIndex : 'url',
	sortable : true,
	id : 'url',
	name : 'url',
	width : 200
}, {
	header : 'Title',
	dataIndex : 'title',
	sortable : true,
	id : 'title',
	name : 'title',
	width : 100
}, {
	header : 'Hidden URL',
	dataIndex : 'hiddenUrl',
	sortable : true,
	id : 'hiddenUrl',
	name : 'hiddenUrl',
	width : 200
},
{
	header : 'Frequency',
	dataIndex : 'freq',
	sortable : true,
	id : 'freq',
	name : 'freq',
	width : 200
},


{
	header : 'Description',
	dataIndex : 'desc',
	sortable : true,
	id : 'desc',
	name : 'desc',
	width : 600,
	renderer : function(value, metadata, record, rowIndex, colIndex, store) {
		metadata.tdAttr = 'data-qtip="' + value + '"';
		return value;

	}
} ];

var hideConfirmationMsg;
var showConfirmationMsg;
/* Hide the Confirmation Message */
hideConfirmationMsg = function() {
	var confMsgDiv = Ext.get('confirmationMessage');
	confMsgDiv.dom.innerHTML = "";
	confMsgDiv.dom.style.display = 'none';
}
/* Show Confirmation Message */
showConfirmationMsg = function(msg) {
	var confMsgDiv = Ext.get('confirmationMessage');
	confMsgDiv.dom.innerHTML = msg;
	confMsgDiv.dom.style.display = 'inline-block';
}

Ext.onReady(function() {
	Ext.define('reviewModel', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'url',
			mapping : 'url',
			type : 'string'
		}, {
			name : 'title',
			mapping : 'title',
			type : 'string'
		}, {
			name : 'desc',
			mapping : 'desc',
			type : 'string'
		}, {
			name : 'hiddenUrl',
			mapping : 'hiddenUrl',
			type : 'string'
		}, {
			name : 'userId',
			mapping : 'userId',
			type : 'string'
		}, {
			name : 'desc',
			mapping : 'desc',
			type : 'string'
		}, {
			name : 'query',
			mapping : 'query',
			type : 'string'
		}, {
			name : 'tokenName',
			mapping : 'tokenName',
			type : 'string'
		},{
			name : 'freq',
			mapping : 'freq',
			type : 'int'
		},
		{
			name : 'idft',
			mapping : 'idft',
			type : 'double'
			
		},
		{
			name : 'featureVector',
			mapping : 'featureVector',
			type : 'double'
			
		},
		{
			name : 'featureId',
			mapping : 'featureId',
			type : 'int'
			
		},
		
			{
				name : 'noOfReviews',
				mapping : 'noOfReviews',
				type : 'int'
				
			}
		
		
		]

	});

	var reviewStore = Ext.create('Ext.data.Store', {
		id : 'reviewStoreId',
		name : 'reviewStoreName',
		model : 'reviewModel',
		proxy : {
			type : 'ajax',
			url : contextPath + '/review/viewFeatureVector.do',
			actionMethods : {
				read : 'POST'
			},
			reader : {
				type : 'json',
				root : 'model'
			}
		},
		listeners : {
			'load' : function(store, records) {
			}
		},
		autoLoad : true
	});
	reviewStore.load();

	var reviewGrid = Ext.create('Ext.grid.Panel', {
		collapsible : true,
		title : 'Feature Vector Results',
		forceFit : true,
		id : 'reviewGrid',
		store : reviewStore,
		columns : reviewColumns,
		height : 400,
		width : 1400,
		autoFit : true,
		stripRows : true,
		renderTo : 'reviewGridContainer'
	});

});
