angular.module('bcFactories')

	.factory('persistFactory', [function() {

		var container = {};

		var put = function(key, value) {
			container[key] = value;
		};

		var retrieve = function(key) {
			return container[key];
		};

		return {
			put: put,
			retrieve: retrieve
		};

	}]);