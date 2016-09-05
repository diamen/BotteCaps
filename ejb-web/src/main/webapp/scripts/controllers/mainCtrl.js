angular.module('bcControllers', [])
	.controller('mainCtrl', function($scope, $state, $sessionStorage, $rootScope) {

		$rootScope.$storage = $sessionStorage;

		/* Redirect */
		$scope.go = function(state) {
			$state.go(state);
		};

		$scope.redirectToEditNews = function(id) {
			$state.go("news.edit", { id: id });
		};

		$scope.redirectToCountry = function(country) {
			$state.go("collect.country", { country: country });
		};

		$scope.addCapRedirect = function(country) {
			$state.go("collect.country.add", { country: country });
		};

		$scope.openCap = function(country, id) {
			$state.go("collect.country.id", { country: country, id: id });
		};

		$scope.openEditCap = function(country, id) {
			$state.go("collect.country.id.edit", { country: country, id: id });
		};

});