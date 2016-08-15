angular.module('bcControllers')
	.controller('editCapCtrl', function($scope, $window, $stateParams, $uibModal, restService, base64Service) {

		$scope.country = $stateParams.country;
		$scope.id = $stateParams.id;
		$scope.showAlert = false;

		$scope.beerOptions = [ "Niepiwo", "Piwo" ];

		$scope.closeAlert = function() {
			$scope.showAlert = false;
		};

		$scope.cancel = function() {
			$window.location.reload();
		};

		restService.photoController().getCountryFlag($scope.country).success(function(data) {
			$scope.flag = data.flag;
		});

		$scope.update = function(id, country, captext, capbrand, beer) {
			var beerLabel = beer === 'Piwo' ? 1 : 0;
			restService.adminController().updateCap(id, country, captext, capbrand, beerLabel)
			.success(function() {
				$scope.$parent.redirectToCountry($scope.country);
			})
			.error(function() {
				$scope.showAlert = true;
			});
		};

		restService.photoController().getSingleCap($scope.country, $scope.id).success(function(data) {
			$scope.cap = data;
			$scope.capsrc = base64Service.base64ToUrl(data.base64);

			$scope.beerLabel = data.beer === 1 ? 'Piwo' : 'Niepiwo';

			restService.photoController().getBrand(data.brand_id).success(function(data) {
				$scope.brand = data;
			});
		});

		/* modal */
		$scope.openModal = function(type) {

			if(type === 'CAN') {
				$scope.msg = "Czy chcesz wycofac zmiany?";
				$scope.invoke = $scope.cancel;
			}

			if(type === 'OK') {
				$scope.msg = "Czy chcesz zatwierdzic zmiany?";
				$scope.invoke = partial($scope.update, $scope.id, $scope.country, $scope.cap.cap_text, $scope.brand, $scope.beerLabel);
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