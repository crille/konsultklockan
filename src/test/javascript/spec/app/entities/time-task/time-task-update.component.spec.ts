/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KonsultklockanTestModule } from '../../../test.module';
import { TimeTaskUpdateComponent } from 'app/entities/time-task/time-task-update.component';
import { TimeTaskService } from 'app/entities/time-task/time-task.service';
import { TimeTask } from 'app/shared/model/time-task.model';

describe('Component Tests', () => {
    describe('TimeTask Management Update Component', () => {
        let comp: TimeTaskUpdateComponent;
        let fixture: ComponentFixture<TimeTaskUpdateComponent>;
        let service: TimeTaskService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KonsultklockanTestModule],
                declarations: [TimeTaskUpdateComponent]
            })
                .overrideTemplate(TimeTaskUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TimeTaskUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimeTaskService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TimeTask('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.timeTask = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TimeTask();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.timeTask = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
