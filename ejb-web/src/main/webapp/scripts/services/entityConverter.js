angular.module('bcServices')
	.service("entityConverter", ['base64Service', function(base64Service) {
		return function(data) {
			var entities = [];

			for(var i = 0; i < data.length; i++) {
				var src = base64Service.base64ToUrl(data[i].base64);
				entities.push({src: src, entity: data[i].entity});
			}

			return entities;
		};
	}])