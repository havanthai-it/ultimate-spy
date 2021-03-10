import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SpyAdsDetailComponent } from './spy-ads-detail/spy-ads-detail.component';
import { SpyComponent } from './spy.component';

const routes: Routes = [
    {
        path: '',
        component: SpyComponent,
        children: []
    },
    {
        path: ':id',
        component: SpyAdsDetailComponent,
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
