import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SpyComponent } from './spy.component';

const routes: Routes = [
    {
        path: '',
        component: SpyComponent,
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

export class SpyRoutingModule {}
