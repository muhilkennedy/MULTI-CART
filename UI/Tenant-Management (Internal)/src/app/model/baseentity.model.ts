export class BaseEntity {

    private _rootId: string;
    private _uniqueName: string;
    private _timeCreated: number;
    private _timeUpated: number;
    private _active: boolean;

    set rootId(value: string) {
        this._rootId = value;
    }

    get rootId() {
        return this._rootId;
    }

    set uniqueName(value: string) {
        this._uniqueName = value;
    }

    get uniqueName() {
        return this._uniqueName;
    }

    set timeCreated(value: number) {
        this._timeCreated = value;
    }

    get timeCreated() {
        return this._timeCreated;
    }

    set timeUpated(value: number) {
        this._timeUpated = value;
    }

    get timeUpated() {
        return this._timeUpated;
    }

    set active(value: boolean) {
        this._active = value;
    }

    get active() {
        return this._active;
    }

}