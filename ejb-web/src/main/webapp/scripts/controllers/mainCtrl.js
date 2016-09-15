angular.module('bcControllers', [])
	.controller('mainCtrl', function($scope, $state, $sessionStorage, $rootScope) {

		$rootScope.$storage = $sessionStorage;

		$scope.persistedData = {};

		/* Event handlers */
		$scope.passData = function(event, data) {
			$scope.$broadcast(event, data);
		};

		$scope.persist = function(key, data) {
			$scope.persistedData[key] = data;
		};

		$scope.retrieve = function(key) {
			return $scope.persistedData[key];
		};

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