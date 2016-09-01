angular.module('bcControllers')
	.controller('addTradeCapsCtrl', function($scope, $uibModal, base64Service, restService) {

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

		/* modal */
		$scope.openModal = function(type) {

			if(type === 'CLR') {
				$scope.msg = "Czy chcesz wycofac zmiany?";
				$scope.invoke = $scope.clear;
			}

			if(type === 'UPL') {
				$scope.msg = "Czy chcesz dodac kapsle do sekcji wymiany?";
				$scope.invoke = $scope.uploadFiles;
			}

			var modalInstance = $uibModal.open({
			      animation: true,
			      templateUrl: '/ejb-web/views/templates/modal.html',
			      controller: 'modalCtrl',
			      size: 'sm',
			      resolve: {
			        msg: function () {
			          return $scope.msg;
			        }
			      }
			    });

			modalInstance.result.then(function () {
			      $scope.invoke();
			    }, function () {
			      console.log('dismissed');
			    });
		};

	});