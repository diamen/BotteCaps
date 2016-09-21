angular.module('bcFilters', [])
	.filter('toPlCountry', ['language', function(language) {
		return function(input) {
			var out = [];

			if(angular.isUndefined(input))
				return;

			for(var i = 0; i < input.length; i++){
				input[i].namepl = language.countryToPL(input[i].name);
				input[i].nameen = input[i].name;
				out.push(input[i]);
			}

			return out;
		}
}]);