angular.module('bcControllers')
	.controller('userStripCtrl', function($scope, $sessionStorage, $rootScope, restService) {
		
		$scope.logout = function() {
			restService.authController().logout().success(function() {
				delete $rootScope.$storage.authToken;
				delete $sessionStorage.authToken;
			});
		};
		
});