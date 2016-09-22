angular.module('bcControllers')
	.controller('editCapCtrl', function($scope, $window, $stateParams, modalService, restService, base64Service) {

		$scope.country = $stateParams.country;
		$scope.id = $stateParams.id;
		$scope.showAlert = false;

		$scope.beerOptions = [ "Niepiwo", "Piwo" ];

		$scope.closeAlert = function() {
			$scope.showAlert = false;
		};

		restService.countriesController().getFlag($scope.country).success(function(data) {
			$scope.flag = data.flag;
		});

		$scope.update = function(id, country, captext, capbrand, beer) {
			var beerLabel = beer === 'Piwo' ? 1 : 0;
			restService.adminController().editCap(id, country, captext, capbrand, beerLabel)
			.success(function() {
				$scope.$parent.redirectToCountry($scope.country);
			})
			.error(function() {
				$scope.showAlert = true;
			});
		};

		restService.collectController().getSingleCap($scope.country, $scope.id).success(function(data) {
			$scope.cap = data.entity;
			$scope.capsrc = base64Service.base64ToUrl(data.base64);

			$scope.beerLabel = data.entity.beer === 1 ? 'Piwo' : 'Niepiwo';

			restService.collectController().getBrand(data.entity.brand_id).success(function(data) {
				$scope.brand = data;
			});
		});

		$scope.openModalOk = function() {
			modalService.execute(
					partial($scope.update, $scope.id, $scope.country, $scope.cap.cap_text, $scope.brand, $scope.beerLabel),
					"Czy chcesz zatwierdzić zmiany?");
		};

		$scope.openModalCancel = function() {
			modalService.execute(function() {
				$scope.$parent.openCap($scope.country, $scope.id);
			}, "Czy chcesz wycofać zmiany?");
		};

	});