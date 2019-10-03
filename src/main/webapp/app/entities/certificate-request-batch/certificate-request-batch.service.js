(function() {
    'use strict';
    angular
        .module('jhipsterApp')
        .factory('CertificateRequestBatch', CertificateRequestBatch);

    CertificateRequestBatch.$inject = ['$resource', 'DateUtils'];

    function CertificateRequestBatch ($resource, DateUtils) {
        var resourceUrl =  'api/certificate-request-batches/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.creationDate = DateUtils.convertDateTimeFromServer(data.creationDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
