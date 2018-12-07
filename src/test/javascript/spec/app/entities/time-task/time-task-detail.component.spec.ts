/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KonsultklockanTestModule } from '../../../test.module';
import { TimeTaskDetailComponent } from 'app/entities/time-task/time-task-detail.component';
import { TimeTask } from 'app/shared/model/time-task.model';

describe('Component Tests', () => {
    describe('TimeTask Management Detail Component', () => {
        let comp: TimeTaskDetailComponent;
        let fixture: ComponentFixture<TimeTaskDetailComponent>;
        const route = ({ data: of({ timeTask: new TimeTask('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KonsultklockanTestModule],
                declarations: [TimeTaskDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TimeTaskDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TimeTaskDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.timeTask).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
