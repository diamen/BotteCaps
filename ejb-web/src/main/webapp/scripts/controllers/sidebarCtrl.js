angular.module('bcControllers')
	.controller('sidebarCtrl', function($scope, restService, entityConverter) {

		$scope.top = {};
		$scope.progress = {};

		restService.collectController().getNewestCaps().success(function(data) {
			$scope.data = data;
			$scope.caps = entityConverter(data);
		});

		$scope.openCap = function(index) {
			var countryId = $scope.data[index].entity.country_id;

			restService.countriesController().getCountry(countryId).success(function(country) {
				$scope.$parent.openCap(country, $scope.caps[index].id);
			});
		};

		$scope.top.options = {
			    tooltips: {
			    	  callbacks: {
			    		  title: function() {
			    			return '';
			    		  },
			    		  label: function(tooltipItem, data) {
			    			  return $scope.top.fullLabel[tooltipItem.index] + " = " + data.datasets[0].data[tooltipItem.index];
			    		  }
			    	  }
			      }
		};

		restService.countriesController().getCountriesWithAmount().success(function(data) {
			data.sort(function(a, b) {
				return b.amount - a.amount;
			});

			$scope.top.labels = [];
			$scope.top.fullLabel = [];
			$scope.top.chartData = [];

			angular.forEach(data, function(entity) {
				$scope.top.labels.push(entity.name.substring(0,3).toUpperCase());
				$scope.top.fullLabel.push(entity.name);
				$scope.top.chartData.push(entity.amount);
			});
		});

		restService.collectController().getCapsAmountProgress().success(function(data) {
			$scope.progress.labels = [];
			$scope.progress.chartData = [];

			angular.forEach(data, function(entity) {
				$scope.progress.labels.push(entity.added_date);
				$scope.progress.chartData.push(entity.amount);
			});
		});

	});