import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IProject } from 'app/shared/model//project.model';

export interface ITimeTask {
    id?: string;
    start?: Moment;
    stop?: Moment;
    note?: string;
    user?: IUser;
    project?: IProject;
}

export class TimeTask implements ITimeTask {
    constructor(
        public id?: string,
        public start?: Moment,
        public stop?: Moment,
        public note?: string,
        public user?: IUser,
        public project?: IProject
    ) {}
}
