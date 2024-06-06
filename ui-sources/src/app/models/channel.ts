import {UserDTO} from "../layout/users-view/users-view.component";

export class Channel {
  constructor(public id: number, public user: UserDTO, public name: string, public description: string, public photoUrl: string) {}
}
