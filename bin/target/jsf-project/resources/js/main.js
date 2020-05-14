$(document).ready(function() {
	
	/*var utils = $.pivotUtilities;
	var heatmap = utils.renderers["Heatmap"];
	var sumOverSum = utils.aggregators["Sum over Sum"];*/
	

	$.getJSON("http://10.15.119.17:8003/crms/pivot", function(data) {
		$("#output").pivotUI(data, {}, false, "vi");
	});

	$.getJSON("http://10.15.119.17:8003/crms/pivot", function(data) {
		var total_count = function(){
			return {
				count: 0,
				push: function(recode){this.count++},
				value: function(){return this.count;},
				format: function(x){return x;},
			};
		};
		
		$("#output_pivot").pivot(data, {
			rows:["id", "name"],
			cols:["sex", "dob"],
			rowOrder: "key_a_to_z",
			count: total_count
		});
	});
	
	$.ajax({
		type: 'GET',
		url: 'http://10.15.119.17:8003/crms/pivot',
		dataType: 'json',
		success: function(data){
			$("#output_hsdn").pivotUI(data, {}, false, "vi");
		}
	});

});
