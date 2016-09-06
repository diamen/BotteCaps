angular.module('bcControllers')
	.controller('capCtrl', function($scope, $stateParams, restService, base64Service, shareData, capMover) {

		angular.element(document.querySelector('.sidebar')).removeClass("sidebar");

		$scope.country = $stateParams.country;
		$scope.id = $stateParams.id;

		restService.countriesController().getFlag($scope.country).success(function(data) {
			$scope.flag = data.flag;
		});

		restService.collectController().getSingleCap($scope.country, $scope.id).success(function(data) {
			$scope.cap = data.entity;
			$scope.capsrc = base64Service.base64ToUrl(data.base64);

			$scope.beerLabel = data.entity.beer === 1 ? 'Piwo' : 'Niepiwo';

			restService.collectController().getBrand(data.entity.brand_id).success(function(data) {
				$scope.brand = data;
			});
		});

		$scope.caps = shareData.getData();

		$scope.selectFiveCaps = function(capId) {

			// TODO Nie działa gdy wchodzi się na przedostatni / ostatni kapsel
			
			if($scope.caps === undefined)
				return;

			var fivecaps = [];
			var capIndex = $scope.caps.map(function(e) { return e.id; }).indexOf(capId);

			var mover = capMover.capMover($scope.caps);

			if(mover.isFirst(capIndex))
				fivecaps = mover.updateFirstTwo(fivecaps);

			if(mover.isSecond(capIndex))
				fivecaps = mover.updateFirst(fivecaps);

			mover.updateOthers(fivecaps, capId);

			if(mover.isLast(capIndex))
				fivecaps = mover.updateLastTwo(fivecaps);

			if(mover.isNextToLast(capIndex))
				fivecaps = mover.updateLast(fivecaps);

			$scope.fivecaps = fivecaps;
			$scope.mainsrc = fivecaps[2].src;
		};

		$scope.selectFiveCaps(parseInt($scope.id));

		$scope.previousCap = function() {
			$scope.selectFiveCaps(parseInt($scope.fivecaps[1].id));
		};

		$scope.nextCap = function() {
			$scope.selectFiveCaps($scope.fivecaps[3].id);
		};

});