angular.module('bcControllers')
	.controller('collectCtrl', function($scope, $window, $state, $stateParams, restService, base64Service, markService, modalService, entityConverter, shareData) {

		$scope.isMoreThanPage = false;
		$scope.markedIds = [];
		$scope.country = $stateParams.country || 'Albania';
		$scope.orderCapsOptions = [{name: 'Alfabetycznie', value: 'cap_text'}, {name: 'Najstarsze', value: '-added_date'}, {name: 'Najnowsze', value: 'added_date'}];
		$scope.orderCaps = $scope.orderCapsOptions[0].value;

		$scope.pagination = {
			currentPage: 1,
			totalItems: 0,
			maxPerPage: 49
		};

		var getAmountFromArr = function(arr, country) {
			return arr.filter(function(elem) {
				return elem.name === country;
			})[0].amount;
		};

		$scope.$on('$stateChangeSuccess',
				function(event, toState, toParams, fromState, fromParams) {
			var country = $scope.country || toParams.country;
			var arr = $scope.$parent.retrieve('countryAmount');

			if(arr) {
				$scope.pagination.totalItems = getAmountFromArr(arr, country);
				$scope.isMoreThanPage = $scope.pagination.totalItems > $scope.pagination.maxPerPage;
				$scope.getPageData();
			}
		});

		$scope.$on('countryAmountEvent', function(event, data) {
			$scope.$parent.persist('countryAmount', data);
			$scope.pagination.totalItems = getAmountFromArr(data, $scope.country);
			$scope.isMoreThanPage = $scope.pagination.totalItems > $scope.pagination.maxPerPage;
			$scope.getPageData();
		});

		restService.countriesController().getFlag($scope.country).success(function(data) {
			$scope.flag = data.flag;
		});

		if($scope.country === undefined)
			$scope.country = 'Albania';

		$scope.getPageData = function() {
			var page = Math.ceil(($scope.pagination.maxPerPage * $scope.pagination.currentPage) / $scope.pagination.maxPerPage);

			restService.collectController().getCaps($scope.country, page, $scope.pagination.maxPerPage).success(function(data) {
				$scope.caps = entityConverter(data);
				shareData.addData($scope.caps);
			});
		};

		$scope.markCap = function(capId) {
			$scope.markedIds = markService($scope.markedIds, capId);
			console.log($scope.markedIds);
		};

		$scope.deleteFiles = function() {

			var numberOfFiles = $scope.markedIds.length;
			var i = 0;
			var deleteFile = function(capId) {
				restService.adminController().deleteCap(capId).success(function() {

					if(i + 1 < numberOfFiles) {
						i += 1;
						deleteFile($scope.markedIds[i]);
					} else {
						$window.location.reload();
					}
				});
			};

			deleteFile($scope.markedIds[0]);

		};

		$scope.filterCaps = function(captext) {
			captext = captext.toLowerCase();
			$scope.capsTemp = $scope.capsTemp === undefined ? $scope.caps : $scope.capsTemp;
			$scope.caps = [];

			if($scope.isMoreThanPage) {
				restService.collectController().getFilteredCaps($scope.country, captext).success(function(data) {
					$scope.caps = entityConverter(data);
				});
			} else {
				$scope.capsTemp.filter(function(elem) {
					if(elem.entity.cap_text.toLowerCase().includes(captext))
						$scope.caps.push(elem);
				});

			}
		};

		$scope.openModal = function() {
			modalService.execute($scope.deleteFiles, "Czy chcesz usunąć zaznaczone kapsle?");
		};

	});