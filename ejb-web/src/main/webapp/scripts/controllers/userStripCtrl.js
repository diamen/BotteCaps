angular.module('bcControllers')
	.controller('userStripCtrl', function($scope, $http, $sessionStorage, $rootScope, restService) {
		
		$scope.logout = function() {
			restService.authController().logout().success(function() {
				delete $rootScope.$storage.authToken;
				delete $sessionStorage.authToken;
			});
		};
		
});