angular.module('bcServices')
	.service("shareData", [function() {
		var data;
		
		var addData = function(obj) {
			data = obj;
		};
		
		var getData = function() {
			return data;
		};
		
		return {
			addData: addData,
			getData: getData
		};
	}])