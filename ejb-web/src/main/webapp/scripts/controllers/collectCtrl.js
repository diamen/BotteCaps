angular.module('bcControllers')
	.controller('collectCtrl', function($scope, $window, $state, $stateParams, restService, base64Service, markService, modalService, entityConverter, shareData, persistFactory, language) {

		$scope.language = language;
		$scope.isMoreThanPage = false;
		$scope.markedIds = [];
		$scope.country = $stateParams.country || 'Albania';
		$scope.countryInfo = {};

		if($scope.country === undefined)
			$scope.country = 'Albania';

		$scope.kind = {
			NOBEER: {value: 0, name: "Nobeer"},
			BEER: {value: 1, name: "Beer"},
			AMOUNT: {value: 2, name: "Amount"}
		};

		$scope.chosenKind = $scope.kind.AMOUNT;

		$scope.pagination = {
			currentPage: 1,
			totalItems: 0,
			maxPerPage: 49
		};

		var getCountryInfo = function(arr, country) {
			return arr.filter(function(elem) {
				return elem.name === country;
			})[0];
		};

		$scope.$on('$stateChangeSuccess',
				function(event, toState, toParams, fromState, fromParams) {
			var country = $scope.country || toParams.country;
			var arr = persistFactory.retrieve('countryAmount');

			if(arr) {
				$scope.countryInfo = getCountryInfo(arr, country);
				$scope.flag = $scope.countryInfo.flag;
				$scope.getCaps();
			}
		});

		$scope.$on('countryAmountEvent', function(event, data) {
			$scope.$parent.persist('countryAmount', data);
			$scope.countryInfo = getCountryInfo(data, $scope.country);
			$scope.flag = $scope.countryInfo.flag;
			$scope.getCaps();
		});

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

		$scope.getCaps = function(captext, kind) {
			var isStopped = false;
			$scope.captext = captext === '' ? undefined : captext;
			var page = angular.isDefined($scope.captext) ? 1 : Math.ceil(($scope.pagination.maxPerPage * $scope.pagination.currentPage) / $scope.pagination.maxPerPage);
			$scope.pagination.currentPage = page;

			$scope.chosenKind = angular.isDefined(kind) ? kind : $scope.chosenKind;
			$scope.capsTemp = $scope.capsTemp === undefined ? $scope.caps : $scope.capsTemp;
			$scope.caps = [];

			if(angular.isUndefined($scope.capsTemp)) {
				isStopped = true;
				restService.collectController().getCaps($scope.country, $scope.chosenKind.value, page, $scope.pagination.maxPerPage, $scope.captext).success(function(data) {
					$scope.capsTemp = entityConverter(data);
					$scope.getCaps(captext, kind);
				});
			}

			if(!isStopped) {
				$scope.pagination.totalItems = $scope.countryInfo.amount;
				$scope.isMoreThanPage = $scope.pagination.totalItems > $scope.pagination.maxPerPage;

				if($scope.isMoreThanPage) {
					$scope.captext = angular.isUndefined($scope.captext) ? undefined : $scope.captext.toLowerCase().trim();
					restService.collectController().getCaps($scope.country, $scope.chosenKind.value, page, $scope.pagination.maxPerPage, $scope.captext).success(function(data) {
						$scope.caps = entityConverter(data);
						$scope.pagination.totalItems = angular.isDefined($scope.captext) ? $scope.caps.length : $scope.countryInfo[$scope.chosenKind.name.toLowerCase()];
						$scope.isMoreThanPage = $scope.pagination.totalItems > $scope.pagination.maxPerPage;
						shareData.addData($scope.caps);
					});
				} else {
					$scope.capsTemp.filter(function(elem) {
						$scope.captext = angular.isUndefined($scope.captext) ? '' : $scope.captext.toLowerCase().trim();

						var belongs = function(kind) {
							return $scope.chosenKind.value === 2 ? true : kind === $scope.chosenKind.value;
						};

						if(elem.entity.cap_text.toLowerCase().includes($scope.captext) && belongs(elem.entity.beer))
							$scope.caps.push(elem);

						shareData.addData($scope.caps);
					});

				}
			}
		};

		$scope.openModal = function() {
			modalService.execute($scope.deleteFiles, "Czy chcesz usunąć zaznaczone kapsle?");
		};

	});