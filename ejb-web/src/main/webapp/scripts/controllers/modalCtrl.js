angular.module('bcControllers')
	.controller('modalCtrl', function ($scope, $uibModalInstance, msg) {
	
		$scope.msg = msg;
		
		$scope.ok = function () {
		  $uibModalInstance.close();
		};
	
	    $scope.cancel = function () {
	      $uibModalInstance.dismiss('cancel');
	    };
});