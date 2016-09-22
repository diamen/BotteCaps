angular.module('bcControllers')
	.controller('addTradeCapsCtrl', function($scope, modalService, base64Service, restService) {

		$scope.clear = function() {
			$scope.files.length = 0;
		};

		$scope.msg = '';
		$scope.showAlert = false;

		$scope.closeAlert = function() {
			$scope.showAlert = false;
		};

		$scope.uploadFiles = function(idx) {

			if(idx === undefined)
				idx = 0;

			var numberOfFiles = $scope.files.length;
			var i = idx;

			base64Service.imgToBase64($scope.files[i].src, 'image/jpeg', function(base64) {
				restService.adminController().addTrade(base64, $scope.files[i].name)
					.success(function() {
						if(i + 1 < numberOfFiles) {
							i += 1;
							$scope.uploadFiles(i);
						} else {
							$scope.$parent.go('trade');
						}
					}).error(function(data) {
						$scope.msg = data["message"];
						$scope.showAlert = true;
					});
			});
		};

		$scope.openModalOk = function() {
			modalService.execute($scope.uploadFiles, "Czy chcesz dodać kapsle do kolekcji?");
		};

		$scope.openModalCancel = function() {
			modalService.execute(function() {
				$scope.files.length = 0;
			}, "Czy chcesz wycofać zmiany?");
		};

});