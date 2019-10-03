(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('certificate-request-batch', {
            parent: 'entity',
            url: '/certificate-request-batch?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.certificateRequestBatch.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/certificate-request-batch/certificate-request-batches.html',
                    controller: 'CertificateRequestBatchController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('certificateRequestBatch');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('certificate-request-batch-detail', {
            parent: 'certificate-request-batch',
            url: '/certificate-request-batch/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterApp.certificateRequestBatch.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/certificate-request-batch/certificate-request-batch-detail.html',
                    controller: 'CertificateRequestBatchDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('certificateRequestBatch');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CertificateRequestBatch', function($stateParams, CertificateRequestBatch) {
                    return CertificateRequestBatch.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'certificate-request-batch',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('certificate-request-batch-detail.edit', {
            parent: 'certificate-request-batch-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/certificate-request-batch/certificate-request-batch-dialog.html',
                    controller: 'CertificateRequestBatchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CertificateRequestBatch', function(CertificateRequestBatch) {
                            return CertificateRequestBatch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('certificate-request-batch.new', {
            parent: 'certificate-request-batch',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/certificate-request-batch/certificate-request-batch-dialog.html',
                    controller: 'CertificateRequestBatchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                creationDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('certificate-request-batch', null, { reload: 'certificate-request-batch' });
                }, function() {
                    $state.go('certificate-request-batch');
                });
            }]
        })
        .state('certificate-request-batch.edit', {
            parent: 'certificate-request-batch',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/certificate-request-batch/certificate-request-batch-dialog.html',
                    controller: 'CertificateRequestBatchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CertificateRequestBatch', function(CertificateRequestBatch) {
                            return CertificateRequestBatch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('certificate-request-batch', null, { reload: 'certificate-request-batch' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('certificate-request-batch.delete', {
            parent: 'certificate-request-batch',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/certificate-request-batch/certificate-request-batch-delete-dialog.html',
                    controller: 'CertificateRequestBatchDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CertificateRequestBatch', function(CertificateRequestBatch) {
                            return CertificateRequestBatch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('certificate-request-batch', null, { reload: 'certificate-request-batch' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
