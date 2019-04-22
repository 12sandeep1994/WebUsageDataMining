Ext.require('Ext.chart.*');
Ext.require('Ext.layout.container.Fit');
Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.form.*', 'Ext.layout.container.Column', 'Ext.tab.Panel']);
Ext.Loader.setConfig({
    enabled: true
});



Ext.onReady(function () {
	
	Ext.define('fvModel', {
		extend : 'Ext.data.Model',
		fields : [ 
		           {name:'productId', mapping:'productId',type:'int'},
		           {name:'productType', mapping:'productType',type:'int'},
		           {name:'featureVector', mapping:'featureVector',type:'double'}
				  ]
	});
	
	var energyCompareStoreAlgo1Store = Ext.create('Ext.data.Store', {
		id : 'energyCompareStoreId1',
		name : 'energyCompareStoreName1',
		model : 'fvModel',
		proxy : {
			type : 'ajax',
			url :contextPath+'/review/fvgraph.do',
			extraParams:{
				type:'2'
			},
			actionMethods:{
				read:'POST'
			},
			reader : {
				type :'json',
				root:'model',
				totalProperty:'totalSize'
			}
		},
		listeners:
		{
			'load':function(store, records){
		}			
		},
		autoLoad : true
	});
energyCompareStoreAlgo1Store.load();
	
Ext.create('Ext.chart.Chart', {
    renderTo: Ext.getBody(),
    width: 500,
    height: 300,
    animate: true,
    store: energyCompareStoreAlgo1Store,
    axes: [{
        type: 'Numeric',
        position: 'bottom',
        fields: ['featureVector'],
        label: {
            renderer: Ext.util.Format.numberRenderer('0,0')
        },
        title: 'Feature Vector',
        grid: true,
        minimum: 0
    }, {
        type: 'Category',
        position: 'left',
        fields: ['productId'],
        title: 'Product Id'
    }],
    series: [{
        type: 'bar',
        axis: 'bottom',
        highlight: true,
        tips: {
          trackMouse: true,
          width: 140,
          height: 28,
          renderer: function(storeItem, item) {
            this.setTitle(storeItem.get('productId') + ': ' + storeItem.get('featureVector') + ' Feature Vector');
          }
        },
        label: {
          display: 'insideEnd',
            field: 'rating',
            renderer: Ext.util.Format.numberRenderer('0'),
            orientation: 'horizontal',
            color: '#333',
            'text-anchor': 'middle'
        },
        xField: 'productId',
        yField: 'featureVector'
    }]
});		

});    
           