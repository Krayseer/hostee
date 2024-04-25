import {User} from "./user";

export class report {
  constructor(public id: number,
              public userTarget: User,
              public userSender: User,
              public reportState: string,
              public text: string,
              public solver: User,
              public solveResult: string) {}
}
