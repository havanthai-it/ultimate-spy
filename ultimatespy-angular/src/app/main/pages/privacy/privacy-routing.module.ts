import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PrivacyComponent } from './privacy.component';

const routes: Routes = [
    {
        path: '',
        component: PrivacyComponent,
        children: []
    }
]

@NgModule({
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [
        RouterModule
    ]
})

export class PrivacyRoutingModule {}
