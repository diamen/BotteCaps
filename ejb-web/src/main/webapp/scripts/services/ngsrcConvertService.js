angular.module('bcServices')
	.service("ngsrcConvertService", function () {
		return {
			convert: function (cap) {
				return cap.path + cap.file_name + "." + cap.extension.toLowerCase();
			}
		};
	});