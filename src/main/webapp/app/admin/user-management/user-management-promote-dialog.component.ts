import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { User, UserService } from 'app/core';

@Component({
    selector: 'jhi-user-mgmt-promote-dialog',
    templateUrl: './user-management-promote-dialog.component.html'
})
export class UserMgmtPromoteDialogComponent {
    user: User;

    constructor(private userService: UserService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmPromote(login) {
        // this.userService.promote(login).subscribe(response => {
        //     this.eventManager.broadcast({
        //         name: 'userListModification',
        //         content: 'Promoted a user'
        //     });
        this.activeModal.dismiss(true);
        // });
    }
}
