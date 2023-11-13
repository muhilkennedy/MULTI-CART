import { BaseEntity } from "./baseentity.model";

export class User extends BaseEntity {
    private _userName: string;
    private _userEmail: string;
    private _userId: string;
    private _userActive: boolean;

    private _token: string;

    constructor() {
        super();
    }

    set userName(name: string) {
        this._userName = name;
    }

    set userId(id: string) {
        this._userId = id;
    }

    set userEmail(email: string) {
        this._userEmail = email;
    }

    set userActive(active: boolean) {
        this._userActive = active;
    }

    set token(token: string) {
        this._token = token;
    }

    get token() {
        return this._token;
    }

    get userName() {
        return this._userName;
    }

    get userEmail() {
        return this._userEmail;
    }

    get isActive() {
        return this._userActive;
    }

    get userId() {
        return this._userId;
    }
}
